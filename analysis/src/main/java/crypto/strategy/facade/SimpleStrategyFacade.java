package crypto.strategy.facade;

import crypto.repository.DbStrategiesService;
import crypto.strategy.domain.simplestrategy.SimpleStrategy;
import crypto.strategy.domain.simplestrategy.SimpleStrategyDto;
import crypto.strategy.domain.simplestrategy.SimpleStrategyResponse;
import crypto.strategy.mapper.SimpleStrategyMapper;
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
        savedStrategyInfo =  dbStrategiesService.saveSimpleStrategy(simpleStrategy);
        if (savedStrategyInfo != null) {
            log.info("Individual Strategy was saved to database!");
            return new SimpleStrategyResponse(simpleStrategyMapper.convertToSimpleStrategyDto(savedStrategyInfo),true);
        }else {
            log.error("Something went wrong during saving strategy to database!");
            return new SimpleStrategyResponse(new SimpleStrategyDto(), false);
        }
    }
}
