package crypto.okex.client;

import com.google.gson.Gson;
import crypto.okex.authentication.OkexExchangeAuthentication;
import crypto.okex.domain.accountbalance.OkexAccountInfoDto;
import crypto.okex.domain.order.OkexCreatedOrderDto;
import crypto.okex.domain.order.OkexOrderDto;
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
public class OkexNewOrderClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OkexNewOrderClient.class);

    @Value("${okex.order.cancel}")
    private String newOrderPath;

    @Autowired
    private OkexExchangeAuthentication exchangeAuthentication;

    public OkexCreatedOrderDto placeNewOrder(OkexOrderDto orderDto) {
        OkexCreatedOrderDto createdOrderDto = null;
        OkexParamsModerator okexParamsModerator = new OkexParamsModerator(OkexParams.WITHOUT_PARAMS.getParams(), orderDto);
        try {
            String result = exchangeAuthentication.requestHttpPost(newOrderPath, okexParamsModerator);
            System.out.println(result);
            Gson gson = new Gson();
            createdOrderDto = gson.fromJson(result, OkexCreatedOrderDto.class);
            LOGGER.info("Account information successfully downloaded!");
        }catch (HttpException e) {
            LOGGER.error("Something wrong with the connection " + e.getMessage());
        }catch (IOException e) {
            LOGGER.error("Something wrong with the data conversion " + e.getMessage());
        }
        return createdOrderDto;
    }
}
