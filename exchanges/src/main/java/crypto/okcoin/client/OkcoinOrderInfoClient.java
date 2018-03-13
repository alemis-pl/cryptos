package crypto.okcoin.client;

import com.google.gson.Gson;
import crypto.okcoin.authentication.OkcoinExchangeAuthentication;
import crypto.okcoin.domain.order.OkcoinOrderById;
import crypto.okcoin.domain.order.OkcoinOrdersInfoDto;
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
public class OkcoinOrderInfoClient {

    @Value("${okex.order.info}")
    private String orderInfoPath;

    private OkcoinExchangeAuthentication exchangeAuthentication;

    public OkcoinOrderInfoClient(OkcoinExchangeAuthentication exchangeAuthentication) {
        this.exchangeAuthentication = exchangeAuthentication;
    }

    public OkcoinOrdersInfoDto getOrdersInfo(OkcoinOrderById orderById) {
        OkcoinOrdersInfoDto ordersInfo = null;
        System.out.println(orderById);
        OkcoinParamsModerator okexParamsModerator = new OkcoinParamsModerator(OkcoinParams.ORDER_BY_ID.getParams(), orderById);
        System.out.println(okexParamsModerator);
        try {
            String result = exchangeAuthentication.requestHttpPost(orderInfoPath, okexParamsModerator);
            Gson gson = new Gson();
            ordersInfo = gson.fromJson(result, OkcoinOrdersInfoDto.class);
            log.info("Orders [" + ordersInfo + "] information successfully downloaded!" );
        }catch (HttpException e) {
            log.error("Something wrong with the connection " + e.getMessage());
        }catch (IOException e) {
            log.error("Something wrong with the data conversion " + e.getMessage());
        }
        return ordersInfo;
    }
}
