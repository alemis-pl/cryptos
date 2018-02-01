package crypto.okex.client;

import com.google.gson.Gson;
import crypto.okex.authentication.OkexExchangeAuthentication;
import crypto.okex.domain.order.OkexOrderById;
import crypto.okex.domain.order.OkexOrderHistoryRequest;
import crypto.okex.domain.order.OkexOrdersHistoryDto;
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
public class OkexOrdersHistoryClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OkexOrdersHistoryClient.class);

    @Value("${okex.order.history}")
    private String orderHistoryPath;

    @Autowired
    private OkexExchangeAuthentication exchangeAuthentication;

    public OkexOrdersHistoryDto getOrdersHistory(OkexOrderHistoryRequest okexOrderHistoryRequest) {
        OkexOrdersHistoryDto ordersHistory = null;
        OkexParamsModerator okexParamsModerator = new OkexParamsModerator(OkexParams.ORDERS_HISTORY.getParams(), okexOrderHistoryRequest);
        try {
            String result = exchangeAuthentication.requestHttpPost(orderHistoryPath, okexParamsModerator);
            Gson gson = new Gson();
            ordersHistory = gson.fromJson(result, OkexOrdersHistoryDto.class);
            LOGGER.info("Orders history successfully downloaded! [" + ordersHistory + "]" );
        }catch (HttpException e) {
            LOGGER.error("Something wrong with the connection " + e.getMessage());
        }catch (IOException e) {
            LOGGER.error("Something wrong with the data conversion " + e.getMessage());
        }
        return ordersHistory;
    }
}
