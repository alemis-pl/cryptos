package crypto.oanda.client;

import crypto.oanda.authentication.OandaAuthentication;
import crypto.oanda.authentication.OandaRequestType;
import crypto.oanda.authentication.OandaUrlCreator;
import crypto.oanda.authentication.OandaUrlType;
import crypto.oanda.domain.price.OandaPrice;
import crypto.persistance.apikey.ApiKeys;
import crypto.persistance.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class OandaPriceDownloader {

    private OandaAuthentication authentication;
    private OandaUrlCreator urlCreator;
    private DbService dbService;

    @Autowired
    public OandaPriceDownloader(OandaAuthentication authentication, OandaUrlCreator urlCreator,DbService dbService) {
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
        String url =  urlCreator.createUrl(OandaUrlType.PRICES.getUrlType(),accountId, instrument);
        HttpEntity entity = authentication.createHeaders(token, accountId, OandaRequestType.STANDARD_REQUEST.getRequestType());
        Optional<OandaPrice> price = authentication.getResponse(url,entity, OandaPrice.class,  HttpMethod.GET);
        System.out.println(price);
        return price.orElse(new OandaPrice());
    }
}
