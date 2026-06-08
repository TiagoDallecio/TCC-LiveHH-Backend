package br.com.livehh.livehh_engine.domain.entity.usecase;

import br.com.livehh.livehh_engine.domain.entity.HandHistory;
import org.springframework.stereotype.Service;

@Service
public class ProcessHandHistoryUseCaseImpl implements ProcessHandHistoryUseCase {

    @Override
    public String execute(HandHistory handHistory){

        System.out.println("Iniciando processamento da mão: " + handHistory.getHandId());
        System.out.println(("O hero está no assento: "+ handHistory.getHero().getSeat()));

        return handHistory.getHandId();
    }
}
