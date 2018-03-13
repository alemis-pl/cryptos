package crypto.okcoin.client;

import com.google.gson.Gson;
import crypto.okcoin.authentication.OkcoinExchangeAuthentication;
import crypto.okcoin.domain.accountbalance.OkcoinAccountInfoDto;;
import crypto.okcoin.domain.params.OkcoinParams;
import crypto.okcoin.domain.params.OkcoinParamsModerator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.apache.http.HttpException;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class OkcoinAccountInfoClient {

    @Value("${okex.main.url}")
    private String mainUrl;

    @Value("${okex.accountbalance.info}")
    private String accountInfoPath;

    private OkcoinExchangeAuthentication exchangeAuthentication;

    public OkcoinAccountInfoClient(OkcoinExchangeAuthentication exchangeAuthentication) {
        this.exchangeAuthentication = exchangeAuthentication;
    }

    public OkcoinAccountInfoDto getAccountInfo() {
        OkcoinAccountInfoDto accountInfo = null;
        OkcoinParamsModerator okexParamsModerator = new OkcoinParamsModerator(OkcoinParams.WITHOUT_PARAMS.getParams());

        try {
            String result = exchangeAuthentication.requestHttpPost(accountInfoPath, okexParamsModerator);
            Gson gson = new Gson();
            accountInfo = gson.fromJson(result, OkcoinAccountInfoDto.class);
            log.info("Account information successfully downloaded! [" + accountInfo + "]");
        }catch (HttpException e) {
            log.error("Something wrong with the connection " + e.getMessage());
        }catch (IOException e) {
            log.error("Something wrong with the data conversion " + e.getMessage());
        }
        return accountInfo;
    }
}
