package crypto.bittrex.client;

import crypto.apikeys.ApiKeys;
import crypto.apikeys.ApiKeysRepository;
import crypto.bittrex.authentication.BittrexExchangeAuthentication;
import crypto.bittrex.domain.accountbalance.BittrexAccountBalanceDto;
import crypto.bittrex.domain.accountbalance.BittrexAccountBalancesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;


@Slf4j
@Component
public class BittrexAccountManager {

    @Value("${bittrex.main.url}")
    private String bittrexMainUrl;

    @Value("${bittrex.account.balances}")
    private String accountBalancesUrl;

    @Value("${bittrex.account.balance}")
    private String accountBalanceUrl;

    @Value("${bittrex.apikey}")
    private String apiKeyUrl;

    private Long nonce = System.currentTimeMillis();

    private BittrexExchangeAuthentication bittrexExchangeAuthentication;
    private ApiKeysRepository apiKeysRepository;
    private RestTemplate restTemplate;

    public BittrexAccountManager(BittrexExchangeAuthentication bittrexExchangeAuthentication, ApiKeysRepository apiKeysRepository, RestTemplate restTemplate) {
        this.bittrexExchangeAuthentication = bittrexExchangeAuthentication;
        this.apiKeysRepository = apiKeysRepository;
        this.restTemplate = restTemplate;
    }

    private ApiKeys getApiKeys() {
         return apiKeysRepository.getByExchange("bittrex");
    }

    public BittrexAccountBalancesDto getAccountBalances() {
        ApiKeys apiKeys = getApiKeys();
        String apiKey = apiKeys.getApiKey();
        String url = bittrexMainUrl + accountBalancesUrl + apiKeyUrl + apiKey + "&nonce=" + nonce;
        HttpEntity entity = bittrexExchangeAuthentication.createHttpEntity(url, apiKeys.getApiSecretKey());
        Optional<BittrexAccountBalancesDto> accountBalances = bittrexExchangeAuthentication.getResponse(url, entity, BittrexAccountBalancesDto.class, HttpMethod.POST);
        return accountBalances.orElseGet(BittrexAccountBalancesDto::new);
    }

    public BittrexAccountBalanceDto getAccountBalance(String currency) {
        ApiKeys apiKeys = getApiKeys();
        String apiKey = apiKeys.getApiKey();
        String url = bittrexMainUrl + accountBalanceUrl + apiKeyUrl + apiKey + "&nonce=" + nonce + "&currency=" + currency;
        HttpEntity entity = bittrexExchangeAuthentication.createHttpEntity(url, apiKeys.getApiSecretKey());
        Optional<BittrexAccountBalanceDto> accountBalance = bittrexExchangeAuthentication.getResponse(url, entity, BittrexAccountBalanceDto.class, HttpMethod.POST);
        return accountBalance.orElseGet(BittrexAccountBalanceDto::new);
    }
}
