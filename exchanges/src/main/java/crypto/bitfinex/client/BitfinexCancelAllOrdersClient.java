package crypto.bitfinex.client;

import com.google.gson.Gson;
import crypto.bitfinex.authentication.BitfinexExchangeAuthentication;
import crypto.bitfinex.authentication.BitfinexExchangeConnectionExceptions;
import crypto.bitfinex.authentication.BitfinexExchangeHttpResponse;
import crypto.bitfinex.domain.order.BitfinexCancelAllOrders;
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
public class BitfinexCancelAllOrdersClient {

    @Value("${bitfinex.order.cancel.all}")
    private String cancelAllOrder;

    @Autowired
    private BitfinexExchangeAuthentication exchangeAuthentication;

    private static final Logger LOGGER = LoggerFactory.getLogger(BitfinexCancelAllOrdersClient.class);

    private static final String POST = "POST";

    public BitfinexCancelAllOrders cancelAllOrders() throws Exception {

        BitfinexParamsToSearch paramsToSearch = null;
        BitfinexParamsModerator paramsModerator = new BitfinexParamsModerator(BitfinexParams.WITHOUT_PARAMS.getParams(), paramsToSearch);

        try {
            BitfinexExchangeHttpResponse exchangeHttpResponse = exchangeAuthentication.sendExchangeRequest(cancelAllOrder, POST, paramsModerator);
            LOGGER.info("Cancel all orders information: " + exchangeHttpResponse);

            Gson gson = new Gson();

            return  gson.fromJson(exchangeHttpResponse.getContent(), BitfinexCancelAllOrders.class);
        } catch (IOException e) {
            LOGGER.error(BitfinexExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
            throw new IOException(BitfinexExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
        } catch (NullPointerException e) {
            LOGGER.error(BitfinexExchangeConnectionExceptions.CANCEL_ORDER_ERROR.getException(), e);
            throw new IOException(BitfinexExchangeConnectionExceptions.CANCEL_ORDER_ERROR.getException(), e);
        }
    }
}
