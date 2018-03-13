package crypto.okcoin.client;

import com.google.gson.Gson;
import crypto.okcoin.authentication.OkcoinExchangeAuthentication;
import crypto.okcoin.domain.order.OkcoinCreatedOrderDto;
import crypto.okcoin.domain.order.OkcoinOrderDto;
import crypto.okcoin.domain.params.OkcoinParams;
import crypto.okcoin.domain.params.OkcoinParamsModerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OkcoinNewOrderClient {

    @Value("${okex.order.new}")
    private String newOrderPath;

    private OkcoinExchangeAuthentication exchangeAuthentication;

    public OkcoinNewOrderClient(OkcoinExchangeAuthentication exchangeAuthentication) {
        this.exchangeAuthentication = exchangeAuthentication;
    }

    public OkcoinCreatedOrderDto placeNewOrder(OkcoinOrderDto orderDto) {
        OkcoinCreatedOrderDto createdOrderDto = null;
        OkcoinParamsModerator okexParamsModerator = new OkcoinParamsModerator(OkcoinParams.NEW_ORDER.getParams(), orderDto);
        try {
            String result = exchangeAuthentication.requestHttpPost(newOrderPath, okexParamsModerator);
            System.out.println(result);
            Gson gson = new Gson();
            createdOrderDto = gson.fromJson(result, OkcoinCreatedOrderDto.class);
            log.info("New order successfully placed! [ " + createdOrderDto.getOrder_id() +" ]");
        }catch (HttpException e) {
            log.error("Something wrong with the connection " + e.getMessage());
        }catch (IOException e) {
            log.error("Something wrong with the data conversion " + e.getMessage());
        }
        return createdOrderDto;
    }
}
