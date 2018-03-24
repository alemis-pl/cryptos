package crypto.bittrex.client;

import crypto.bittrex.authentication.BittrexExchangeAuthentication;
import crypto.bittrex.domain.ticker.BittrexTicker;
import crypto.bittrex.domain.ticker.BittrexTickerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class BittrexMarketDataManager {

    @Value("${bittrex.main.url}")
    private String bittrexMainUrl;

    @Value("${bittrex.public.ticker}")
    private String tickerUrl;

    private BittrexExchangeAuthentication bittrexExchangeAuthentication;

    public BittrexMarketDataManager(BittrexExchangeAuthentication bittrexExchangeAuthentication) {
        this.bittrexExchangeAuthentication = bittrexExchangeAuthentication;
    }

    public BittrexTickerResponse getTicker(String currencyPair) {
        String url = bittrexMainUrl + tickerUrl + currencyPair;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(headers);
        BittrexTickerResponse ticker = new BittrexTickerResponse();
        Optional<BittrexTicker> tickerResponse = bittrexExchangeAuthentication.getResponse(url, entity, BittrexTicker.class, HttpMethod.GET);
        tickerResponse.ifPresent(bittrexTicker -> {
            if (bittrexTicker.isSuccess()) {
                ticker.setCurrency(currencyPair);
                ticker.setPrices(tickerResponse.get().getResult());
                log.info("Ticker for " + currencyPair + " was downloaded!" );
            }else {
                log.error("Something went wrong with downloading ticker for " + currencyPair);
            }
        });
        return ticker;
    }
}
