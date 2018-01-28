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
public class OrdersHistoryClient {

    @Value("${bitfinex.order.history}")
    private String ordersHistory;

    @Autowired
    private ExchangeAuthentication exchangeAuthentication;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersHistoryClient.class);

    private static final String POST = "POST";

    public CreatedOrderDto[] getOrdersHistory() throws Exception {

        ParamsToSearch paramsToSearch = null;
        ParamsModerator paramsModerator = new ParamsModerator(Params.WITHOUT_PARAMS.getParams(), paramsToSearch);

        try {
            ExchangeHttpResponse exchangeHttpResponse = exchangeAuthentication.sendExchangeRequest(ordersHistory, POST, paramsModerator);
            LOGGER.info("Orders history information: " + exchangeHttpResponse);

            Gson gson = new Gson();

            CreatedOrderDto[] createdOrderDtos = gson.fromJson(exchangeHttpResponse.getContent(), CreatedOrderDto[].class);

            return  createdOrderDtos;
        } catch (IOException e) {
            LOGGER.error(ExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
            throw new IOException(ExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
        } catch (NullPointerException e) {
            LOGGER.error(ExchangeConnectionExceptions.CANCEL_ORDER_ERROR.getException(), e);
            throw new IOException(ExchangeConnectionExceptions.CANCEL_ORDER_ERROR.getException(), e);
        }
    }
}
