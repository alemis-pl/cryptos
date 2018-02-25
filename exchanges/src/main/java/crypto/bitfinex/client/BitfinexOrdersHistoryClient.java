package crypto.bitfinex.client;

import com.google.gson.Gson;
import crypto.authentication_help.ExchangeHttpResponse;
import crypto.bitfinex.authentication.BitfinexExchangeAuthentication;
import crypto.bitfinex.authentication.BitfinexExchangeConnectionExceptions;
import crypto.bitfinex.domain.order.BitfinexCreatedOrderDto;
import crypto.bitfinex.domain.params.BitfinexParams;
import crypto.bitfinex.domain.params.BitfinexParamsModerator;
import crypto.bitfinex.domain.params.BitfinexParamsToSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BitfinexOrdersHistoryClient {

    @Value("${bitfinex.order.history}")
    private String ordersHistory;

    @Autowired
    private BitfinexExchangeAuthentication exchangeAuthentication;

    private static final Logger LOGGER = LoggerFactory.getLogger(BitfinexOrdersHistoryClient.class);

    private static final String POST = "POST";

    public BitfinexCreatedOrderDto[] getOrdersHistory() throws Exception {

        BitfinexParamsToSearch paramsToSearch = null;
        BitfinexParamsModerator paramsModerator = new BitfinexParamsModerator(BitfinexParams.WITHOUT_PARAMS.getParams(), paramsToSearch);

        try {
            ExchangeHttpResponse exchangeHttpResponse = exchangeAuthentication.sendExchangeRequest(ordersHistory, POST, paramsModerator);
            LOGGER.info("Orders history information: " + exchangeHttpResponse);

            Gson gson = new Gson();

            BitfinexCreatedOrderDto[] createdOrderDtos = gson.fromJson(exchangeHttpResponse.getContent(), BitfinexCreatedOrderDto[].class);

            return  createdOrderDtos;
        } catch (IOException e) {
            LOGGER.error(BitfinexExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
            throw new IOException(BitfinexExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
        } catch (NullPointerException e) {
            LOGGER.error(BitfinexExchangeConnectionExceptions.CANCEL_ORDER_ERROR.getException(), e);
            throw new IOException(BitfinexExchangeConnectionExceptions.CANCEL_ORDER_ERROR.getException(), e);
        }
    }
}
