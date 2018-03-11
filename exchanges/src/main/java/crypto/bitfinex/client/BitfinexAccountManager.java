package crypto.bitfinex.client;

import crypto.bitfinex.authentication.BitfinexExchangeAuthenticationSpring;
import crypto.bitfinex.domain.accountbalance.BitfinexAccountBalanceHistoryListDto;
import crypto.bitfinex.domain.accountbalance.BitfinexAccountBalanceListDto;
import crypto.bitfinex.domain.params.BitfinexParams;
import crypto.bitfinex.domain.params.BitfinexParamsModerator;
import crypto.bitfinex.domain.params.BitfinexParamsToSearch;
import crypto.bitfinex.domain.pasttrades.BitfinexPastTradesListDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Slf4j
@Component
public class BitfinexAccountManager {

    @Value("${bitfinex.main.url}")
    private String bitfinexMainUrl;

    @Value("${bitfinex.accountbalance.current}")
    private String accountBalance;

    @Value("${bitfinex.accountbalance.history}")
    private String accountHistory;

    @Value("${bitfinex.pasttrades}")
    private String pastTrades;

    private BitfinexExchangeAuthenticationSpring exchangeAuthenticationSpring;

    @Autowired
    public BitfinexAccountManager(BitfinexExchangeAuthenticationSpring exchangeAuthenticationSpring) {
        this.exchangeAuthenticationSpring = exchangeAuthenticationSpring;
    }

    public BitfinexAccountBalanceListDto getAccountBalance(String params) {
        BitfinexParamsToSearch paramsToSearch = null;
        BitfinexParamsModerator paramsModerator = new BitfinexParamsModerator(params, paramsToSearch);
        String url = bitfinexMainUrl + accountBalance;
        HttpEntity entity = exchangeAuthenticationSpring.createHttpEntity(paramsModerator, accountBalance);
        Optional<BitfinexAccountBalanceListDto> accountBalance = exchangeAuthenticationSpring.getResponse(url, entity, BitfinexAccountBalanceListDto.class, HttpMethod.POST);
        if (accountBalance.isPresent()) {
            log.info("Account balance information downloaded!");
        }
        return accountBalance.orElse(new BitfinexAccountBalanceListDto());
    }

    public BitfinexAccountBalanceHistoryListDto getAccountBalanceHistory(BitfinexParamsToSearch paramsToSearch, String param) {
        BitfinexParamsModerator paramsModerator = new BitfinexParamsModerator(param, paramsToSearch);
        String url = bitfinexMainUrl + accountHistory;
        HttpEntity entity = exchangeAuthenticationSpring.createHttpEntity(paramsModerator, accountHistory);
        Optional<BitfinexAccountBalanceHistoryListDto> accountBalanceHistory = exchangeAuthenticationSpring.getResponse(url, entity, BitfinexAccountBalanceHistoryListDto.class, HttpMethod.POST);
        if (accountBalanceHistory.isPresent()) {
            log.info("Account balance history information downloaded!");
        }
        return accountBalanceHistory.orElse(new BitfinexAccountBalanceHistoryListDto());
    }

   public BitfinexPastTradesListDto getPastTrades(String symbol, String timestamp)  {
       BitfinexParamsToSearch paramsToSearch = new BitfinexParamsToSearch(symbol,Long.valueOf(timestamp));
       BitfinexParamsModerator paramsModerator = new BitfinexParamsModerator(BitfinexParams.PAST_TRADES.getParams(), paramsToSearch);
       String url = bitfinexMainUrl + pastTrades;
       HttpEntity entity = exchangeAuthenticationSpring.createHttpEntity(paramsModerator, pastTrades);
       Optional<BitfinexPastTradesListDto> pastTrades = exchangeAuthenticationSpring.getResponse(url, entity, BitfinexPastTradesListDto.class, HttpMethod.POST);
       if (pastTrades.isPresent()) {
           log.info("Past trades information downloaded!");
       }
       return pastTrades.orElse(new BitfinexPastTradesListDto());
   }
}