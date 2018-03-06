package crypto.oanda.client;

import crypto.oanda.authentication.OandaAuthentication;
import crypto.oanda.domain.price.OandaPrice;
import crypto.oanda.domain.price.OandaPriceList;
import crypto.persistance.apikey.ApiKeys;
import crypto.persistance.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


@Configuration
public class OandaPricesClient {

    @Value("${oanda.main.url}")
    private String oandaMainUrl;

    @Value("${oanda.prices}")
    private String pricesUrl;

    private OandaAuthentication authentication;
    private RestTemplate restTemplate;
    private DbService dbService;

    @Autowired
    public OandaPricesClient(OandaAuthentication authentication, RestTemplate restTemplate, DbService dbService) {
        this.authentication = authentication;
        this.restTemplate = restTemplate;
        this.dbService = dbService;
    }

    private ApiKeys getApiKeys() {
        return dbService.getApiKeysByExchange("oanda");
    }

    public OandaPrice getPrice(String instrument) {
        String token = getApiKeys().getApiKey();
        String accountId = getApiKeys().getClientId();
        String url = createUrl(accountId, instrument);
        HttpEntity entity = authentication.createHeaders(token);
        ResponseEntity<OandaPrice> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, OandaPrice.class);
        OandaPrice price = responseEntity.getBody();
        System.out.println(price);
        return price;
    }

    private String createUrl(String accountId, String instrument) {
        StringBuilder url = new StringBuilder();
        url.append(oandaMainUrl);
        url.append(accountId);
        url.append("/");
        url.append(pricesUrl);
        url.append(instrument);
        System.out.println(url.toString());
        return url.toString();
    }
}
