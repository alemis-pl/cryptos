package crypto.repository;

import crypto.strategy.domain.simplestrategy.SimpleStrategy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface SimpleStrategyRepository extends CrudRepository<SimpleStrategy, Long> {

    SimpleStrategy save(SimpleStrategy strategyInfo);

    List<SimpleStrategy> findAll();

    List<SimpleStrategy> findAllByExchange(String exchange);

    List<SimpleStrategy> findAllByAchieved(boolean achieved);
}
