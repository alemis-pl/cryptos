package crypto.bittrex.controller;

import crypto.bittrex.client.BittrexAccountManager;
import crypto.bittrex.domain.accountbalance.BittrexAccountBalanceDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("v1/crypto")
public class BittrexAccountManagementController {

    private BittrexAccountManager bittrexAccountManager;

    public BittrexAccountManagementController(BittrexAccountManager bittrexAccountManager) {
        this.bittrexAccountManager = bittrexAccountManager;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/bittrex_account_balance")
    public BittrexAccountBalanceDto getBalanceAccount() {
        return bittrexAccountManager.getAccountBalance();
    }
}
