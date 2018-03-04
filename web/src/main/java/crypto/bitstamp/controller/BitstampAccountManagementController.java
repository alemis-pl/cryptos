package crypto.bitstamp.controller;

import crypto.bitstamp.client.BitstampAccountBalanceClient;
import crypto.bitstamp.domain.accountbalance.BitstampAccountBalanceListDto;
import crypto.bitstamp.domain.parmas.BitstampParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/crypto")
public class BitstampAccountManagementController {


    @Autowired
    private BitstampAccountBalanceClient accountBalanceClient;

    @RequestMapping(method = RequestMethod.GET, value = "bitstamp_account_balance")
    public BitstampAccountBalanceListDto getUserAccountBalance() throws Exception {
        return accountBalanceClient.getAccountBalance(BitstampParams.WITHOUT_PARAMS.getParams());
    }
}
