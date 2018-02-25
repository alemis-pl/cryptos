package crypto.bitfinex.client;

import com.google.gson.Gson;
import crypto.authentication_help.ExchangeHttpResponse;
import crypto.bitfinex.authentication.BitfinexExchangeAuthentication;
import crypto.bitfinex.authentication.BitfinexExchangeConnectionExceptions;
import crypto.bitfinex.domain.accountbalance.BitfinexAccountBalanceHistoryListDto;
import crypto.bitfinex.domain.params.BitfinexParamsModerator;
import crypto.bitfinex.domain.params.BitfinexParamsToSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BitfinexAccountBalanceHistoryClient {

    @Value("${bitfinex.accountbalance.history}")
    private String accountHistory;

    @Autowired
    private BitfinexExchangeAuthentication exchangeAuthentication;


    private static final Logger LOGGER = LoggerFactory.getLogger(BitfinexAccountBalanceHistoryClient.class);

    private static final String POST = "POST";

    public BitfinexAccountBalanceHistoryListDto getAccountBalanceHistory(BitfinexParamsToSearch paramsToSearch, String param) throws Exception {

        BitfinexParamsModerator paramsModerator = new BitfinexParamsModerator(param, paramsToSearch);

        try {
            ExchangeHttpResponse exchangeHttpResponse = exchangeAuthentication.sendExchangeRequest(accountHistory, POST, paramsModerator);

            LOGGER.info("Account balance history information: " + exchangeHttpResponse);

            Gson gson = new Gson();

            BitfinexAccountBalanceHistoryListDto accountBalanceHistoryDtos = gson.fromJson(exchangeHttpResponse.getContent(), BitfinexAccountBalanceHistoryListDto.class);
            accountBalanceHistoryDtos.stream().forEach(System.out::println);

            return accountBalanceHistoryDtos;
        } catch (Exception e) {
            LOGGER.error(BitfinexExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
            throw new IOException(BitfinexExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
        }
    }
}
