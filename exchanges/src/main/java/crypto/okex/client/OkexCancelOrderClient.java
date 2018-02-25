package crypto.okex.client;

import com.google.gson.Gson;
import crypto.okex.authentication.OkexExchangeAuthentication;
import crypto.okex.domain.order.OkexOrderById;
import crypto.okex.domain.order.OkexCanceldOrderResponseDto;
import crypto.okex.domain.params.OkexParams;
import crypto.okex.domain.params.OkexParamsModerator;
import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OkexCancelOrderClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OkexCancelOrderClient.class);

    @Value("${okex.order.cancel}")
    private String cancelOrderPath;

    @Autowired
    private OkexExchangeAuthentication exchangeAuthentication;

    public OkexCanceldOrderResponseDto cancelOrder(OkexOrderById canceldOrderDto) {
        OkexCanceldOrderResponseDto canceldOrderResponseDto = null;
        OkexParamsModerator okexParamsModerator = new OkexParamsModerator(OkexParams.ORDER_BY_ID.getParams(), canceldOrderDto);
        try {
            String result = exchangeAuthentication.requestHttpPost(cancelOrderPath, okexParamsModerator);
            System.out.println(result);
            Gson gson = new Gson();
            canceldOrderResponseDto = gson.fromJson(result, OkexCanceldOrderResponseDto.class);
            LOGGER.info("Order ["+ canceldOrderResponseDto.getOrder_id() + "] successfully canceled!");
        }catch (HttpException e) {
            LOGGER.error("Something wrong with the connection " + e.getMessage());
        }catch (IOException e) {
            LOGGER.error("Something wrong with the data conversion " + e.getMessage());
        }
        return canceldOrderResponseDto;
    }
}
