package br.com.livehh.livehh_engine.config;

import br.com.livehh.livehh_engine.domain.service.BetClassificationDomainService;
import br.com.livehh.livehh_engine.domain.strategy.CalibratedEvCalculator;
import br.com.livehh.livehh_engine.domain.strategy.StrictEvCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public StrictEvCalculator strictEvCalculator() {
        return new StrictEvCalculator();
    }

    @Bean
    public CalibratedEvCalculator calibratedEvCalculator(StrictEvCalculator strictEvCalculator) {
        return new CalibratedEvCalculator(strictEvCalculator);
    }

    @Bean
    public BetClassificationDomainService betClassificationDomainService() {
        return new BetClassificationDomainService();
    }
}
