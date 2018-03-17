package crypto.bitfinex.client;

import crypto.bitfinex.domain.tickers.BitfinexTicker;
import lombok.extern.slf4j.Slf4j;
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

    public Optional<BitfinexTicker> downloadTickersData(String instrument) {
        String url = tickerGetRequest + instrument;
        log.info("Start of downloading tickers data [request: " + url + "]");
        BitfinexTicker ticker = restTemplate.getForObject(url, BitfinexTicker.class);
        System.out.println(ticker);
        return Optional.ofNullable(ticker);
    }
}
