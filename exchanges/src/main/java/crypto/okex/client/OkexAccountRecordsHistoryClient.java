package crypto.okex.client;

import com.google.gson.Gson;
import crypto.okex.authentication.OkexExchangeAuthentication;
import crypto.okex.domain.accountrecords.OkexAccountRecordsListDto;
import crypto.okex.domain.accountrecords.OkexAccountRecordsRequestDto;
import crypto.okex.domain.order.OkexOrderHistoryRequest;
import crypto.okex.domain.order.OkexOrdersHistoryDto;
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
public class OkexAccountRecordsHistoryClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OkexAccountRecordsHistoryClient.class);

    @Value("${okex.account.records}")
    private String accountRecords;

    @Autowired
    private OkexExchangeAuthentication exchangeAuthentication;

    public OkexAccountRecordsListDto getAccountHistory(OkexAccountRecordsRequestDto okexAccountRecordsRequestDto) {
        OkexAccountRecordsListDto accountRecordsListDto = null;
        System.out.println(okexAccountRecordsRequestDto);
        OkexParamsModerator okexParamsModerator = new OkexParamsModerator(OkexParams.ACCOUNT_RECORDS.getParams(), okexAccountRecordsRequestDto);
        try {
            String result = exchangeAuthentication.requestHttpPost(accountRecords, okexParamsModerator);
            System.out.println(result);
            Gson gson = new Gson();
            accountRecordsListDto = gson.fromJson(result, OkexAccountRecordsListDto.class);
            LOGGER.info("Account records successfully downloaded! [" + accountRecordsListDto.toString() + "]" );
        }catch (HttpException e) {
            LOGGER.error("Something wrong with the connection " + e.getMessage());
        }catch (IOException e) {
            LOGGER.error("Something wrong with the data conversion " + e.getMessage());
        }
        return accountRecordsListDto;
    }
}
