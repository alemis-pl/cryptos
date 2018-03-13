package crypto.oanda.client;

import crypto.oanda.authentication.*;
import crypto.oanda.domain.price.OandaPrice;
import crypto.persistance.apikey.ApiKeys;
import crypto.persistance.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class OandaPriceManager {

    private OandaAuthentication authentication;
    private OandaUrlCreator urlCreator;
    private DbService dbService;

    public OandaPriceManager(OandaAuthentication authentication, OandaUrlCreator urlCreator, DbService dbService) {
        this.authentication = authentication;
        this.urlCreator = urlCreator;
        this.dbService = dbService;
    }

    private ApiKeys getApiKeys() {
        return dbService.getApiKeysByExchange("oanda");
    }

    public OandaPrice getPrice(String instrument) {
        String token = getApiKeys().getApiKey();
        String accountId = getApiKeys().getClientId();
        OandaUrlParameters urlParameters = new OandaUrlParameters(OandaUrlType.PRICES.getUrlType(),accountId, instrument);
        OandaHeadersParameters parameters = new OandaHeadersParameters(token,OandaRequestType.STANDARD_REQUEST.getRequestType());
        String url =  urlCreator.createUrl(urlParameters).orElse(new String());
        HttpEntity entity = authentication.createHeaders(parameters);
        Optional<OandaPrice> price = authentication.getResponse(url,entity, OandaPrice.class,  HttpMethod.GET);
        System.out.println(price);
        return price.orElse(new OandaPrice());
    }
}
