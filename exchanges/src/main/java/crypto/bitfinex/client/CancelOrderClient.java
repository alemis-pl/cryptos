package crypto.bitfinex.client;

import com.google.gson.Gson;
import crypto.bitfinex.authentication.ExchangeAuthentication;
import crypto.bitfinex.authentication.ExchangeConnectionExceptions;
import crypto.bitfinex.authentication.ExchangeHttpResponse;
import crypto.bitfinex.domain.order.CreatedOrderDto;
import crypto.bitfinex.domain.params.Params;
import crypto.bitfinex.domain.params.ParamsModerator;
import crypto.bitfinex.domain.params.ParamsToSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CancelOrderClient {

    @Value("${bitfinex.order.cancel}")
    private String cancelOrder;

    @Autowired
    private ExchangeAuthentication exchangeAuthentication;

    private static final Logger LOGGER = LoggerFactory.getLogger(CancelOrderClient.class);

    private static final String POST = "POST";

    public boolean cancelOrder(String orderId) throws Exception {

        ParamsToSearch paramsToSearch = new ParamsToSearch(orderId);
        ParamsModerator paramsModerator = new ParamsModerator(Params.ORDER_BY_ID.getParams(), paramsToSearch);

        try {
            ExchangeHttpResponse exchangeHttpResponse = exchangeAuthentication.sendExchangeRequest(cancelOrder, POST, paramsModerator);
            LOGGER.info("Cancel order information: " + exchangeHttpResponse);

            Gson gson = new Gson();
            gson.fromJson(exchangeHttpResponse.getContent(), CreatedOrderDto.class);

            return true;
        } catch (IOException e) {
            LOGGER.error(ExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
            throw new IOException(ExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
        } catch (NullPointerException e) {
            LOGGER.error(ExchangeConnectionExceptions.CANCEL_ORDER_ERROR.getException(), e);
            throw new IOException(ExchangeConnectionExceptions.CANCEL_ORDER_ERROR.getException(), e);
        }
    }
}
