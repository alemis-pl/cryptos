package crypto.bitfinex.client;

import com.google.gson.Gson;
import crypto.authentication_help.ExchangeHttpResponse;
import crypto.bitfinex.authentication.BitfinexExchangeAuthentication;
import crypto.bitfinex.authentication.BitfinexExchangeConnectionExceptions;
import crypto.bitfinex.domain.order.BitfinexCreatedOrderDto;
import crypto.bitfinex.domain.order.BitfinexOrderDto;
import crypto.bitfinex.domain.params.BitfinexParamsModerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BitfinexNewOrderClient {

    @Value("${bitfinex.order.new}")
    private String newOrder;

    @Autowired
    private BitfinexExchangeAuthentication exchangeAuthentication;

    private static final Logger LOGGER = LoggerFactory.getLogger(BitfinexNewOrderClient.class);

    private static final String POST = "POST";

    public BitfinexCreatedOrderDto createNewOrder(BitfinexOrderDto orderDto, String params) throws Exception {

        BitfinexParamsModerator paramsModerator = new BitfinexParamsModerator(params, orderDto);

        try {
            ExchangeHttpResponse exchangeHttpResponse = exchangeAuthentication.sendExchangeRequest(newOrder, POST, paramsModerator);
            LOGGER.info("New Order information: " + exchangeHttpResponse);

            Gson gson = new Gson();
            BitfinexCreatedOrderDto createdOrderDto = gson.fromJson(exchangeHttpResponse.getContent(), BitfinexCreatedOrderDto.class);

            return createdOrderDto;
        } catch (IOException e) {
            LOGGER.error(BitfinexExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
            throw new IOException(BitfinexExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
        } catch (NullPointerException e) {
            LOGGER.error(BitfinexExchangeConnectionExceptions.RETURN_NULL_AFTER_PLACING_ORDER.getException(), e);
            throw new IOException(BitfinexExchangeConnectionExceptions.RETURN_NULL_AFTER_PLACING_ORDER.getException(), e);
        }
    }
}
