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
public class BitfinexCancelOrderClient {

    @Value("${bitfinex.order.cancel}")
    private String cancelOrder;

    @Autowired
    private BitfinexExchangeAuthentication exchangeAuthentication;

    private static final Logger LOGGER = LoggerFactory.getLogger(BitfinexCancelOrderClient.class);

    private static final String POST = "POST";

    public boolean cancelOrder(String orderId) throws Exception {

        BitfinexParamsToSearch paramsToSearch = new BitfinexParamsToSearch(orderId);
        BitfinexParamsModerator paramsModerator = new BitfinexParamsModerator(BitfinexParams.ORDER_BY_ID.getParams(), paramsToSearch);

        try {
            ExchangeHttpResponse exchangeHttpResponse = exchangeAuthentication.sendExchangeRequest(cancelOrder, POST, paramsModerator);
            LOGGER.info("Cancel order information: " + exchangeHttpResponse);

            Gson gson = new Gson();
            gson.fromJson(exchangeHttpResponse.getContent(), BitfinexCreatedOrderDto.class);

            return true;
        } catch (IOException e) {
            LOGGER.error(BitfinexExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
            throw new IOException(BitfinexExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
        } catch (NullPointerException e) {
            LOGGER.error(BitfinexExchangeConnectionExceptions.CANCEL_ORDER_ERROR.getException(), e);
            throw new IOException(BitfinexExchangeConnectionExceptions.CANCEL_ORDER_ERROR.getException(), e);
        }
    }
}
