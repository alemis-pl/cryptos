package crypto.repository;

import crypto.strategy.domain.simplestrategy.SimpleStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DbStrategiesService {

    @Autowired
    private SimpleStrategyRepository simpleStrategyRepository;


    public SimpleStrategy saveSimpleStrategy(SimpleStrategy individualStrategyInfo) {
        return simpleStrategyRepository.save(individualStrategyInfo);
    }

    public List<SimpleStrategy> getAllSimpleStrategies() {
        return simpleStrategyRepository.findAll();
    }

    public List<SimpleStrategy> getSimpleStrategiesByExchange(String exchange) {
        return simpleStrategyRepository.findAllByExchange(exchange);
    }

    public List<SimpleStrategy> getAllSimpleStrategiesByIsAchieved(boolean achieved) {
        return simpleStrategyRepository.findAllByAchieved(achieved);
    }

}
