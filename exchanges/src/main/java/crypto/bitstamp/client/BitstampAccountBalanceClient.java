package crypto.bitstamp.client;

import com.google.gson.Gson;
import crypto.authentication_help.ExchangeHttpResponse;
import crypto.bitfinex.authentication.BitfinexExchangeConnectionExceptions;
import crypto.bitstamp.authentication.BitstampExchangeAuthentication;
import crypto.bitstamp.authentication.BitstampExchangeConnectionExceptions;
import crypto.bitstamp.domain.accountbalance.BitstampAccountBalanceListDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BitstampAccountBalanceClient {

    @Value("${bitstamp.accountbalance}")
    private String accountBalance;

    private static final Logger LOGGER = LoggerFactory.getLogger(BitstampAccountBalanceClient.class);

    private static final String POST = "POST";

    @Autowired
    private BitstampExchangeAuthentication exchangeAuthentication;

    public BitstampAccountBalanceListDto getAccountBalance(String params) throws Exception {

        try {
            ExchangeHttpResponse exchangeHttpResponse = exchangeAuthentication.sendRequest(accountBalance, POST);

            LOGGER.info("Account balance information: " + exchangeHttpResponse);

            Gson gson = new Gson();

            return gson.fromJson(exchangeHttpResponse.getContent(), BitstampAccountBalanceListDto.class);

        } catch (Exception e) {
            LOGGER.error(BitstampExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
            throw new IOException(BitfinexExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
        }
    }
}
