package crypto.okex.client;

import com.google.gson.Gson;
import crypto.okex.authentication.OkexExchangeAuthentication;
import crypto.okex.domain.accountbalance.OkexAccountInfoDto;
import crypto.okex.domain.order.OkexOrderById;
import crypto.okex.domain.order.OkexOrderInfoListDto;
import crypto.okex.domain.order.OkexOrdersInfoDto;
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
public class OkexOrderInfoClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OkexExchangeAuthentication.class);

    @Value("${okex.order.info}")
    private String orderInfoPath;

    @Autowired
    private OkexExchangeAuthentication exchangeAuthentication;

    public OkexOrdersInfoDto getOrdersInfo(OkexOrderById orderById) {
        OkexOrdersInfoDto ordersInfo = null;
        System.out.println(orderById);
        OkexParamsModerator okexParamsModerator = new OkexParamsModerator(OkexParams.ORDER_BY_ID.getParams(), orderById);
        System.out.println(okexParamsModerator);
        try {
            String result = exchangeAuthentication.requestHttpPost(orderInfoPath, okexParamsModerator);
            Gson gson = new Gson();
            ordersInfo = gson.fromJson(result, OkexOrdersInfoDto.class);
            LOGGER.info("Orders [" + ordersInfo + "] information successfully downloaded!" );
        }catch (HttpException e) {
            LOGGER.error("Something wrong with the connection " + e.getMessage());
        }catch (IOException e) {
            LOGGER.error("Something wrong with the data conversion " + e.getMessage());
        }
        return ordersInfo;
    }
}
