package crypto.okcoin.client;

import com.google.gson.Gson;
import crypto.okcoin.authentication.OkcoinExchangeAuthentication;
import crypto.okcoin.domain.order.OkcoinOrderHistoryRequest;
import crypto.okcoin.domain.order.OkcoinOrdersHistoryDto;
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
public class OkcoinOrdersHistoryClient {

    @Value("${okex.order.history}")
    private String orderHistoryPath;

    private OkcoinExchangeAuthentication exchangeAuthentication;

    public OkcoinOrdersHistoryClient(OkcoinExchangeAuthentication exchangeAuthentication) {
        this.exchangeAuthentication = exchangeAuthentication;
    }

    public OkcoinOrdersHistoryDto getOrdersHistory(OkcoinOrderHistoryRequest okexOrderHistoryRequest) {
        OkcoinOrdersHistoryDto ordersHistory = null;
        OkcoinParamsModerator okexParamsModerator = new OkcoinParamsModerator(OkcoinParams.ORDERS_HISTORY.getParams(), okexOrderHistoryRequest);
        try {
            String result = exchangeAuthentication.requestHttpPost(orderHistoryPath, okexParamsModerator);
            Gson gson = new Gson();
            ordersHistory = gson.fromJson(result, OkcoinOrdersHistoryDto.class);
            log.info("Orders history successfully downloaded! [" + ordersHistory + "]" );
        }catch (HttpException e) {
            log.error("Something wrong with the connection " + e.getMessage());
        }catch (IOException e) {
            log.error("Something wrong with the data conversion " + e.getMessage());
        }
        return ordersHistory;
    }
}
