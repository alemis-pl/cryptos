package crypto.persistance.repository;

import crypto.persistance.apikey.ApiKeys;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ApiKeyRepository extends CrudRepository<ApiKeys, Long> {

    ApiKeys findAllById(Long id);

    @Query
    ApiKeys getByExchange(@Param("EXCHANGE") String exchange);
}
