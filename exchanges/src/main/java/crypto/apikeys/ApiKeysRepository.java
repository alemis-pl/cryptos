package crypto.apikeys;

import crypto.apikeys.ApiKeys;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface ApiKeysRepository extends CrudRepository<ApiKeys, Long> {

    ApiKeys findAllById(Long id);

    @Query
    ApiKeys getByExchange(@Param("EXCHANGE") String exchange);
}
