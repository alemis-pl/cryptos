package forex.oanda.client;

import forex.oanda.authentication.OandaAuthentication;
import forex.oanda.domain.instrument.OandaInstrument;
import forex.oanda.domain.price.OandaPrice;
import forex.oanda.domain.price.OandaPriceList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OandaPricesClient {

    @Value("${oanda.prices}")
    private String pricesUrl;

    @Autowired
    private OandaAuthentication authentication;

    @Autowired
    private RestTemplate restTemplate;

    public OandaPriceList getPrices(String token, String accountId, List<OandaInstrument> instruments) {
        String url = authentication.createUrl(pricesUrl, accountId, instruments);
        HttpEntity entity = authentication.createHeaders(token);
        ResponseEntity<OandaPriceList> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, OandaPriceList.class);
        OandaPriceList priceList = responseEntity.getBody();
        return priceList;
    }
}
