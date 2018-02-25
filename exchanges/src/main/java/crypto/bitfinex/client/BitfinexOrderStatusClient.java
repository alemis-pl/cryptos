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
public class BitfinexOrderStatusClient {

    @Value("${bitfinex.order.status}")
    private String orderStatus;

    @Autowired
    private BitfinexExchangeAuthentication exchangeAuthentication;

    private static final Logger LOGGER = LoggerFactory.getLogger(BitfinexOrderStatusClient.class);

    private static final String POST = "POST";

    public BitfinexCreatedOrderDto getOrdersStatus(String orderId) throws Exception {

        BitfinexParamsToSearch paramsToSearch = new BitfinexParamsToSearch(orderId);
        BitfinexParamsModerator paramsModerator = new BitfinexParamsModerator(BitfinexParams.ORDER_BY_ID.getParams(), paramsToSearch);

        try {
            ExchangeHttpResponse exchangeHttpResponse = exchangeAuthentication.sendExchangeRequest(orderStatus, POST, paramsModerator);
            LOGGER.info("Order status: " + exchangeHttpResponse);

            Gson gson = new Gson();

            return gson.fromJson(exchangeHttpResponse.getContent(), BitfinexCreatedOrderDto.class);
        } catch (IOException e) {
            LOGGER.error(BitfinexExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
            throw new IOException(BitfinexExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
        } catch (NullPointerException e) {
            LOGGER.error(BitfinexExchangeConnectionExceptions.ORDER_STATUS_ERROR.getException(), e);
            throw new IOException(BitfinexExchangeConnectionExceptions.ORDER_STATUS_ERROR.getException(), e);
        }
    }
}
