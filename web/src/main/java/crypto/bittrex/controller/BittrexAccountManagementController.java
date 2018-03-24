package crypto.bittrex.controller;

import crypto.bittrex.client.BittrexAccountManager;
import crypto.bittrex.client.BittrexMarketDataManager;
import crypto.bittrex.client.BittrexOrderManager;
import crypto.bittrex.domain.accountbalance.BittrexAccountBalanceDto;
import crypto.bittrex.domain.accountbalance.BittrexAccountBalancesDto;
import crypto.bittrex.domain.order.*;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("v1/crypto")
public class BittrexAccountManagementController {

    private BittrexAccountManager bittrexAccountManager;
    private BittrexOrderManager bittrexOrderManager;

    public BittrexAccountManagementController(BittrexAccountManager bittrexAccountManager,BittrexOrderManager bittrexOrderManager) {
        this.bittrexAccountManager = bittrexAccountManager;
        this.bittrexOrderManager = bittrexOrderManager;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/bittrex_account_balances")
    public BittrexAccountBalancesDto getAccountBalances() {
        return bittrexAccountManager.getAccountBalances();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/bittrex_account_balance")
    public BittrexAccountBalanceDto getAccountBalance(@RequestParam String currency) {
        return bittrexAccountManager.getAccountBalance(currency);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/bittrex_orders_history")
    public BittrexOrdersDto getOrdersHistory() {
        return bittrexOrderManager.getOrdersHistory();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/bittrex_get_order")
    public BittrexOrderDto getOrder(@RequestParam String orderId) {
        return bittrexOrderManager.getOrder(orderId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/bittrex_place_order")
    public BittrexOrderPlacedResponse placeOrder(@RequestBody BittrexOrderPlaced bittrexOrderPlaced) {
        return bittrexOrderManager.placeOrder(bittrexOrderPlaced);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/bittrex_cancel_order")
    public BittrexOrderCancelResponse cancelOrder(@RequestParam String orderId) {
        return bittrexOrderManager.cancelOrder(orderId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/bittrex_get_open_orders")
    public BittrexOrdersDto getOpenOrders(@RequestParam(required = false) String currencyPair) {
        String currencyOrders = "ALL OPEN ORDERS";
        if (currencyPair != null) {
            currencyOrders = currencyPair;
        }
        return bittrexOrderManager.getOpenOrders(currencyOrders);
    }
}
