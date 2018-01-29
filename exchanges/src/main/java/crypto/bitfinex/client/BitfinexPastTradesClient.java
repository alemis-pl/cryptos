package crypto.bitfinex.client;

import com.google.gson.Gson;
import crypto.bitfinex.authentication.BitfinexExchangeAuthentication;
import crypto.bitfinex.authentication.BitfinexExchangeConnectionExceptions;
import crypto.bitfinex.authentication.BitfinexExchangeHttpResponse;
import crypto.bitfinex.domain.params.BitfinexParams;
import crypto.bitfinex.domain.params.BitfinexParamsModerator;
import crypto.bitfinex.domain.params.BitfinexParamsToSearch;
import crypto.bitfinex.domain.pasttrades.BitfinexPastTradesListDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BitfinexPastTradesClient {

    @Value("${bitfinex.pasttrades}")
    private String pastTrades;

    @Autowired
    private BitfinexExchangeAuthentication exchangeAuthentication;

    private static final Logger LOGGER = LoggerFactory.getLogger(BitfinexPastTradesClient.class);

    private static final String POST = "POST";

    public BitfinexPastTradesListDto getPastTrades(String symbol, String timestamp) throws Exception {

        //only for tests
        BitfinexParamsToSearch paramsToSearch = new BitfinexParamsToSearch(symbol,Long.valueOf(timestamp));
        BitfinexParamsModerator paramsModerator = new BitfinexParamsModerator(BitfinexParams.PAST_TRADES.getParams(), paramsToSearch);

        try {
            BitfinexExchangeHttpResponse exchangeHttpResponse = exchangeAuthentication.sendExchangeRequest(pastTrades, POST, paramsModerator);

            LOGGER.info("Past trades information: " + exchangeHttpResponse);

            Gson gson = new Gson();
            BitfinexPastTradesListDto pastTradesListDto =  gson.fromJson(exchangeHttpResponse.getContent(), BitfinexPastTradesListDto.class);

            return pastTradesListDto;
        } catch (Exception e) {
            LOGGER.error(BitfinexExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
            throw new IOException(BitfinexExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
        }
    }
}
