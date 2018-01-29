package crypto.persistance.mapper;

import crypto.persistance.apikey.ApiKeys;
import crypto.persistance.apikey.ApiKeysDto;
import org.springframework.stereotype.Component;

@Component
public class ApiKeysMapper {

    public ApiKeysDto mapApiKeysToApiKeysDto(ApiKeys apiKeys) {
        return new ApiKeysDto(apiKeys.getApiKey(), apiKeys.getApiSecretKey());
    }
}
