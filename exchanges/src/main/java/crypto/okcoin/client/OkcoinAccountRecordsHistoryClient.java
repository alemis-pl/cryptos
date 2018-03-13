package crypto.okcoin.client;

import com.google.gson.Gson;
import crypto.okcoin.authentication.OkcoinExchangeAuthentication;
import crypto.okcoin.domain.accountrecords.OkcoinAccountRecordsListDto;
import crypto.okcoin.domain.accountrecords.OkcoinAccountRecordsRequestDto;
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
public class OkcoinAccountRecordsHistoryClient {

    @Value("${okex.account.records}")
    private String accountRecords;

    private OkcoinExchangeAuthentication exchangeAuthentication;

    public OkcoinAccountRecordsHistoryClient(OkcoinExchangeAuthentication exchangeAuthentication) {
        this.exchangeAuthentication = exchangeAuthentication;
    }

    public OkcoinAccountRecordsListDto getAccountHistory(OkcoinAccountRecordsRequestDto okexAccountRecordsRequestDto) {
        OkcoinAccountRecordsListDto accountRecordsListDto = null;
        System.out.println(okexAccountRecordsRequestDto);
        OkcoinParamsModerator okexParamsModerator = new OkcoinParamsModerator(OkcoinParams.ACCOUNT_RECORDS.getParams(), okexAccountRecordsRequestDto);
        try {
            String result = exchangeAuthentication.requestHttpPost(accountRecords, okexParamsModerator);
            System.out.println(result);
            Gson gson = new Gson();
            accountRecordsListDto = gson.fromJson(result, OkcoinAccountRecordsListDto.class);
            log.info("Account records successfully downloaded! [" + accountRecordsListDto.toString() + "]" );
        }catch (HttpException e) {
            log.error("Something wrong with the connection " + e.getMessage());
        }catch (IOException e) {
            log.error("Something wrong with the data conversion " + e.getMessage());
        }
        return accountRecordsListDto;
    }
}
