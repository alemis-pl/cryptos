package crypto.persistance.repository;

import crypto.bitfinex.domain.apikey.ApiKeys;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ApiKeyRepository extends CrudRepository<ApiKeys, Long> {

    ApiKeys findAllById(Long id);
}
