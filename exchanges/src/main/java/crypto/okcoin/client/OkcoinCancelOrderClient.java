package crypto.okcoin.client;

import com.google.gson.Gson;
import crypto.okcoin.authentication.OkcoinExchangeAuthentication;
import crypto.okcoin.domain.order.OkcoinOrderById;
import crypto.okcoin.domain.order.OkcoinCanceldOrderResponseDto;
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
public class OkcoinCancelOrderClient {

    @Value("${okex.order.cancel}")
    private String cancelOrderPath;

    private OkcoinExchangeAuthentication exchangeAuthentication;

    public OkcoinCancelOrderClient(OkcoinExchangeAuthentication exchangeAuthentication) {
        this.exchangeAuthentication = exchangeAuthentication;
    }

    public OkcoinCanceldOrderResponseDto cancelOrder(OkcoinOrderById canceldOrderDto) {
        OkcoinCanceldOrderResponseDto canceldOrderResponseDto = null;
        OkcoinParamsModerator okexParamsModerator = new OkcoinParamsModerator(OkcoinParams.ORDER_BY_ID.getParams(), canceldOrderDto);
        try {
            String result = exchangeAuthentication.requestHttpPost(cancelOrderPath, okexParamsModerator);
            System.out.println(result);
            Gson gson = new Gson();
            canceldOrderResponseDto = gson.fromJson(result, OkcoinCanceldOrderResponseDto.class);
            log.info("Order ["+ canceldOrderResponseDto.getOrder_id() + "] successfully canceled!");
        }catch (HttpException e) {
            log.error("Something wrong with the connection " + e.getMessage());
        }catch (IOException e) {
            log.error("Something wrong with the data conversion " + e.getMessage());
        }
        return canceldOrderResponseDto;
    }
}
