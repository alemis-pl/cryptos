package crypto.persistance.service;


import crypto.persistance.apikey.ApiKeys;
import crypto.persistance.repository.ApiKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbService {

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    public ApiKeys getApiKeysById(Long id) {return apiKeyRepository.findAllById(id);}

    public ApiKeys getApiKeysByExchange(String exchange) {
        return apiKeyRepository.getByExchange(exchange);
    }
}
