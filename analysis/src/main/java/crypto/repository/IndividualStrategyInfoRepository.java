package crypto.repository;

import crypto.strategy.individual.domain.signal.SimpleStrategy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface IndividualStrategyInfoRepository extends CrudRepository<SimpleStrategy, Long> {

    SimpleStrategy save(SimpleStrategy strategyInfo);

    @Override
    List<SimpleStrategy> findAll();
}
