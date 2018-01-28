package crypto.persistance.mapper;

import crypto.bitfinex.domain.apikey.ApiKeys;
import crypto.bitfinex.domain.apikey.ApiKeysDto;
import org.springframework.stereotype.Component;

@Component
public class ApiKeysMapper {

    public ApiKeysDto mapApiKeysToApiKeysDto(ApiKeys apiKeys) {
        return new ApiKeysDto(apiKeys.getApiKey(), apiKeys.getApiSecretKey());
    }
}
