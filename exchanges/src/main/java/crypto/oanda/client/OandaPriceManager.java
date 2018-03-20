package crypto.oanda.client;

import crypto.apikeys.ApiKeys;
import crypto.apikeys.ApiKeysRepository;
import crypto.oanda.authentication.*;
import crypto.oanda.domain.price.OandaPrice;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class OandaPriceManager {

    private OandaAuthentication authentication;
    private OandaUrlCreator urlCreator;
    private ApiKeysRepository apiKeysRepository;

    public OandaPriceManager(OandaAuthentication authentication, OandaUrlCreator urlCreator,  ApiKeysRepository apiKeysRepository) {
        this.authentication = authentication;
        this.urlCreator = urlCreator;
        this.apiKeysRepository = apiKeysRepository;
    }

    private ApiKeys getApiKeys() {
        return apiKeysRepository.getByExchange("oanda");
    }

    public Optional<OandaPrice> getPrice(String instrument) {
        String token = getApiKeys().getApiKey();
        System.out.println(token);
        String accountId = getApiKeys().getClientId();
        System.out.println(accountId);
        OandaUrlParameters urlParameters = new OandaUrlParameters(OandaUrlType.PRICES.getUrlType(),accountId, instrument);
        OandaHeadersParameters parameters = new OandaHeadersParameters(token,OandaRequestType.STANDARD_REQUEST.getRequestType());
        String url =  urlCreator.createUrl(urlParameters).orElse(new String());
        System.out.println(url);
        HttpEntity entity = authentication.createHeaders(parameters);
        Optional<OandaPrice> price = authentication.getResponse(url,entity, OandaPrice.class,  HttpMethod.GET);
        return price;
    }
}
