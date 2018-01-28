package crypto.bitfinex.client;

import com.google.gson.Gson;
import crypto.bitfinex.authentication.ExchangeAuthentication;
import crypto.bitfinex.authentication.ExchangeConnectionExceptions;
import crypto.bitfinex.authentication.ExchangeHttpResponse;
import crypto.bitfinex.domain.order.CreatedOrderDto;
import crypto.bitfinex.domain.order.OrderDto;
import crypto.bitfinex.domain.params.ParamsModerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NewOrderClient {

    @Value("${bitfinex.order.new}")
    private String newOrder;

    @Autowired
    private ExchangeAuthentication exchangeAuthentication;

    private static final Logger LOGGER = LoggerFactory.getLogger(NewOrderClient.class);

    private static final String POST = "POST";

    public CreatedOrderDto createNewOrder(OrderDto orderDto, String params) throws Exception {

        ParamsModerator paramsModerator = new ParamsModerator(params, orderDto);

        try {
            ExchangeHttpResponse exchangeHttpResponse = exchangeAuthentication.sendExchangeRequest(newOrder, POST, paramsModerator);
            LOGGER.info("New Order information: " + exchangeHttpResponse);

            Gson gson = new Gson();
            CreatedOrderDto createdOrderDto = gson.fromJson(exchangeHttpResponse.getContent(), CreatedOrderDto.class);

            return createdOrderDto;
        } catch (IOException e) {
            LOGGER.error(ExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
            throw new IOException(ExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
        } catch (NullPointerException e) {
            LOGGER.error(ExchangeConnectionExceptions.RETURN_NULL_AFTER_PLACING_ORDER.getException(), e);
            throw new IOException(ExchangeConnectionExceptions.RETURN_NULL_AFTER_PLACING_ORDER.getException(), e);
        }
    }
}
