package crypto.repository;

import crypto.strategy.individual.domain.signal.SimpleStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DbStrategiesService {

    @Autowired
    private IndividualStrategyInfoRepository individualStrategyInfoRepository;

    public SimpleStrategy saveIndividualStrategy(SimpleStrategy individualStrategyInfo) {
        return individualStrategyInfoRepository.save(individualStrategyInfo);
    }

    public List<SimpleStrategy> getAllIndividualStrategies() {
        return individualStrategyInfoRepository.findAll();
    }

}
