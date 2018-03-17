package crypto.strategy.individual.facade;

import crypto.repository.DbStrategiesService;
import crypto.strategy.individual.domain.signal.SimpleStrategyDto;
import crypto.strategy.individual.domain.signal.SimpleStrategyResponse;
import crypto.strategy.individual.domain.signal.SimpleStrategy;
import crypto.strategy.individual.mapper.SimpleStrategyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleStrategyFacade {

    private DbStrategiesService dbStrategiesService;
    private SimpleStrategyMapper simpleStrategyMapper;

    public SimpleStrategyFacade(DbStrategiesService dbStrategiesService, SimpleStrategyMapper simpleStrategyMapper) {
        this.dbStrategiesService = dbStrategiesService;
        this.simpleStrategyMapper = simpleStrategyMapper;
    }

    public SimpleStrategyResponse addIndividualStrategy(SimpleStrategy simpleStrategy) {
        SimpleStrategy savedStrategyInfo;
        savedStrategyInfo =  dbStrategiesService.saveIndividualStrategy(simpleStrategy);
        if (savedStrategyInfo != null) {
            log.info("Individual Strategy was saved to database!");
            return new SimpleStrategyResponse(simpleStrategyMapper.convertToSimpleStrategyDto(savedStrategyInfo),true);
        }else {
            log.error("Something went wrong during saving strategy to database!");
            return new SimpleStrategyResponse(new SimpleStrategyDto(), false);
        }
    }
}
