package crypto.bitfinex.client;

import com.google.gson.Gson;
import crypto.authentication_help.ExchangeHttpResponse;
import crypto.bitfinex.authentication.BitfinexExchangeAuthentication;
import crypto.bitfinex.authentication.BitfinexExchangeConnectionExceptions;
import crypto.bitfinex.domain.accountbalance.BitfinexAccountBalanceListDto;
import crypto.bitfinex.domain.params.BitfinexParamsModerator;
import crypto.bitfinex.domain.params.BitfinexParamsToSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BitfinexAccountBalanceClient {

    @Value("${bitfinex.accountbalance.current}")
    private String accountBalance;

    @Autowired
    private BitfinexExchangeAuthentication exchangeAuthentication;

    private static final Logger LOGGER = LoggerFactory.getLogger(BitfinexAccountBalanceClient.class);

    private static final String POST = "POST";

    public BitfinexAccountBalanceListDto getAccountBalance(String params) throws Exception {

        BitfinexParamsToSearch paramsToSearch = null;
        BitfinexParamsModerator paramsModerator = new BitfinexParamsModerator(params, paramsToSearch);

        try {
            ExchangeHttpResponse exchangeHttpResponse = exchangeAuthentication.sendExchangeRequest(accountBalance, POST, paramsModerator);

            LOGGER.info("Account balance information: " + exchangeHttpResponse);

            Gson gson = new Gson();

            return gson.fromJson(exchangeHttpResponse.getContent(), BitfinexAccountBalanceListDto.class);
        } catch (Exception e) {
            LOGGER.error(BitfinexExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
            throw new IOException(BitfinexExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
        }
    }
}
