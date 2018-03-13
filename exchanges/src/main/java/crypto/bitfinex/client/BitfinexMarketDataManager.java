package crypto.bitfinex.client;

import crypto.bitfinex.domain.tickers.Ticker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

@Slf4j
@Component
public class BitfinexMarketDataManager {

    @Value("${bitfinex.ticker.request.get}")
    private String tickerGetRequest;

    private RestTemplate restTemplate;

    public BitfinexMarketDataManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<Ticker> downloadTickersData() throws InterruptedException {
        String url = createTestUrl();
        log.info("Start of downloading tickers data [request: " + url + "]");
        Ticker ticker = restTemplate.getForObject(url, Ticker.class);
        System.out.println(ticker);
        return Optional.ofNullable(ticker);
    }

    private String createTestUrl() {
        return tickerGetRequest + "BTCUSD";
    }
}
