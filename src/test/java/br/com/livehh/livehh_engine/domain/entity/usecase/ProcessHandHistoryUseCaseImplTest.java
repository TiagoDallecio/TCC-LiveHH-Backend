package br.com.livehh.livehh_engine.domain.entity.usecase;

import br.com.livehh.livehh_engine.adapter.out.persistence.HandHistoryRepository;
import br.com.livehh.livehh_engine.adapter.out.persistence.entity.HandHistoryJPAEntity; // Ajuste o nome se for JPAEntity
import br.com.livehh.livehh_engine.domain.entity.EpistemicWindow;
import br.com.livehh.livehh_engine.domain.entity.HandHistory;
import br.com.livehh.livehh_engine.domain.strategy.CalibratedEvCalculator;
import br.com.livehh.livehh_engine.domain.strategy.StrictEvCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessHandHistoryUseCaseImplTest {

    @Mock
    private HandHistoryRepository repository;

    @Mock
    private StrictEvCalculator strictEvCalculator;

    @Mock
    private CalibratedEvCalculator calibratedEvCalculator;

    // O Mockito injeta os mocks automaticamente no construtor do nosso UseCase
    @InjectMocks
    private ProcessHandHistoryUseCaseImpl useCase;

    @Test
    void deveExecutarCaminhoEstritoQuandoNaoHouverAmbiguidade() {
        // Arrange
        String rawJson = "{\"status\": \"mock_puro\"}";

        // Mockamos a Entidade de Domínio para controlar a ramificação do IF
        HandHistory handHistory = mock(HandHistory.class);
        when(handHistory.hasAmbiguousWindows()).thenReturn(false);
        when(handHistory.getHandId()).thenReturn("hand-123");
        when(handHistory.getGameType()).thenReturn("NLHE");

        // Forçamos o calculador estrito a retornar um EV conhecido
        when(strictEvCalculator.calculate(any())).thenReturn(50.5);

        // Act
        String resultId = useCase.execute(handHistory, rawJson);

        // Assert
        assertEquals("hand-123", resultId);

        // Verifica se a Strategy correta foi chamada 1 vez, e a outra NENHUMA vez
        verify(strictEvCalculator, times(1)).calculate(any());
        verify(calibratedEvCalculator, never()).calculate(any(), any());

        // Capturador de Argumentos: Intercepta a entidade JPA no momento exato antes de salvar no banco
        ArgumentCaptor<HandHistoryJPAEntity> captor = ArgumentCaptor.forClass(HandHistoryJPAEntity.class);
        verify(repository, times(1)).save(captor.capture());

        HandHistoryJPAEntity savedEntity = captor.getValue();
        assertEquals("hand-123", savedEntity.getHandId());
        assertEquals("NLHE", savedEntity.getGameType());
        assertEquals(50.5, savedEntity.getCalculatedEv()); // O EV deve ser o que mockamos do Strict
        assertEquals(rawJson, savedEntity.getRawPayload());
        assertNotNull(savedEntity.getProcessedAt());
    }

    @Test
    void deveExecutarCaminhoCalibradoQuandoHouverAmbiguidade() {
        // Arrange
        String rawJson = "{\"status\": \"mock_ambiguo\"}";

        HandHistory handHistory = mock(HandHistory.class);
        when(handHistory.hasAmbiguousWindows()).thenReturn(true);
        when(handHistory.getHandId()).thenReturn("hand-456");
        when(handHistory.getGameType()).thenReturn("NLHE");

        EpistemicWindow windowMock = mock(EpistemicWindow.class);
        when(handHistory.getEpistemicWindows()).thenReturn(List.of(windowMock));

        // Forçamos o calculador calibrado a retornar um EV diferente
        when(calibratedEvCalculator.calculate(eq(windowMock), any())).thenReturn(85.0);

        // Act
        String resultId = useCase.execute(handHistory, rawJson);

        // Assert
        assertEquals("hand-456", resultId);

        // Verifica a ramificação
        verify(calibratedEvCalculator, times(1)).calculate(eq(windowMock), any());
        verify(strictEvCalculator, never()).calculate(any());

        // Valida se o banco recebeu a entidade com o cálculo do fluxo Calibrado
        ArgumentCaptor<HandHistoryJPAEntity> captor = ArgumentCaptor.forClass(HandHistoryJPAEntity.class);
        verify(repository).save(captor.capture());

        HandHistoryJPAEntity savedEntity = captor.getValue();
        assertEquals("hand-456", savedEntity.getHandId());
        assertEquals(85.0, savedEntity.getCalculatedEv()); // O EV deve ser do Calibrated
    }
}