package crypto.bitstamp.client;

import crypto.bitstamp.authentication.BitstampExchangeAuthentication;
import crypto.bitstamp.domain.accountbalance.BitstampAccountBalanceListDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Slf4j
@Component
public class BitstampAccountBalanceClient {

    @Value("${bitstamp.main.url}")
    private String bitstampMainUrl;

    @Value("${bitstamp.accountbalance}")
    private String accountBalance;

    private BitstampExchangeAuthentication exchangeAuthentication;

    public BitstampAccountBalanceClient(BitstampExchangeAuthentication exchangeAuthentication) {
        this.exchangeAuthentication = exchangeAuthentication;
    }

    public BitstampAccountBalanceListDto getAccountBalance(String params) {
        HttpEntity entity = exchangeAuthentication.createHttpEntity();
        String url = bitstampMainUrl + accountBalance;
        System.out.println(url);
        Optional<BitstampAccountBalanceListDto> acccountBalance = exchangeAuthentication.getResponse(url, entity, BitstampAccountBalanceListDto.class, HttpMethod.POST);
        return acccountBalance.orElse(new BitstampAccountBalanceListDto());
    }
}
