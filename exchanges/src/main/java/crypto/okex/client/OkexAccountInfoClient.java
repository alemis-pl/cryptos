package crypto.okex.client;

import com.google.gson.Gson;
import crypto.okex.authentication.OkexExchangeAuthentication;
import crypto.okex.domain.accountbalance.OkexAccountInfoDto;;
import crypto.okex.domain.params.OkexParams;
import crypto.okex.domain.params.OkexParamsModerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.http.HttpException;
import java.io.IOException;

@Component
public class OkexAccountInfoClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OkexExchangeAuthentication.class);

    @Value("${okex.accountbalance.info}")
    private String accountInfoPath;

    @Autowired
    private OkexExchangeAuthentication exchangeAuthentication;

    public OkexAccountInfoDto getAccountInfo() {
        OkexAccountInfoDto accountInfo = null;
        OkexParamsModerator okexParamsModerator = new OkexParamsModerator(OkexParams.WITHOUT_PARAMS.getParams());

        try {
            String result = exchangeAuthentication.requestHttpPost(accountInfoPath, okexParamsModerator);
            Gson gson = new Gson();
            accountInfo = gson.fromJson(result, OkexAccountInfoDto.class);
            LOGGER.info("Account information successfully downloaded! [" + accountInfo + "]");
        }catch (HttpException e) {
            LOGGER.error("Something wrong with the connection " + e.getMessage());
        }catch (IOException e) {
            LOGGER.error("Something wrong with the data conversion " + e.getMessage());
        }
        return accountInfo;
    }
}
