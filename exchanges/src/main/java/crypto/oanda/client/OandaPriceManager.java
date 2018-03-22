package crypto.oanda.client;

import crypto.apikeys.ApiKeys;
import crypto.apikeys.ApiKeysRepository;
import crypto.oanda.authentication.*;
import crypto.oanda.domain.price.OandaPrice;
import crypto.oanda.domain.price.OandaPriceList;
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
        String accountId = getApiKeys().getClientId();
        OandaUrlParameters urlParameters = new OandaUrlParameters(OandaUrlType.PRICES.getUrlType(),accountId, instrument);
        OandaHeadersParameters parameters = new OandaHeadersParameters(token,OandaRequestType.STANDARD_REQUEST.getRequestType());
        String url =  urlCreator.createUrl(urlParameters).orElse(new String());
        HttpEntity entity = authentication.createHeaders(parameters);
        Optional<OandaPriceList> prices = authentication.getResponse(url,entity, OandaPriceList.class,  HttpMethod.GET);
        if(prices.isPresent()) {
            Optional<OandaPrice> price = prices.get().getPrices().stream().findFirst();
            return price;
        }
        return Optional.ofNullable(new OandaPrice());
    }
}
