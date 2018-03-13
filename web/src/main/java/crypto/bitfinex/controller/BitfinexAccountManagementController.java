package crypto.bitfinex.controller;

import crypto.bitfinex.client.*;
import crypto.bitfinex.domain.accountbalance.BitfinexAccountBalanceHistoryListDto;
import crypto.bitfinex.domain.accountbalance.BitfinexAccountBalanceListDto;
import crypto.bitfinex.domain.order.BitfinexCancelAllOrders;
import crypto.bitfinex.domain.order.BitfinexCreatedOrderDto;
import crypto.bitfinex.domain.order.BitfinexOrderDto;
import crypto.bitfinex.domain.params.BitfinexParams;
import crypto.bitfinex.domain.params.BitfinexParamsToSearch;
import crypto.bitfinex.domain.pasttrades.BitfinexPastTradesListDto;
import crypto.bitfinex.domain.positions.BitfinexActivePositionsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("v1/crypto")
public class BitfinexAccountManagementController {

    private BitfinexAccountManager accountManager;
    private BitfinexOrderManager orderManager;

    public BitfinexAccountManagementController(BitfinexAccountManager accountManager, BitfinexOrderManager orderManager) {
        this.accountManager = accountManager;
        this.orderManager = orderManager;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/balance")
    private BitfinexAccountBalanceListDto getUserAccountBalance() {
        return accountManager.getAccountBalance(BitfinexParams.WITHOUT_PARAMS.getParams());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/balance-history")
    private BitfinexAccountBalanceHistoryListDto getUserAccountBalanceHistory(@RequestParam String currency,
                                                                              @RequestParam(required = false) Long since,
                                                                              @RequestParam(required = false) Long until,
                                                                              @RequestParam(required = false) String wallet) {

        BitfinexParamsToSearch paramsToSearch = new BitfinexParamsToSearch(currency, since, until, wallet);
        return accountManager.getAccountBalanceHistory(paramsToSearch, BitfinexParams.BALANCE_HISTORY.getParams());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/past-trades")
    public BitfinexPastTradesListDto getPastTrades(@RequestParam String symbol, @RequestParam(required = false) String sinceTimestamp) {
        return accountManager.getPastTrades(symbol,sinceTimestamp);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/positions")
    public BitfinexActivePositionsDto[] getActivePositions() {
        return orderManager.getActivePositions();
    }


    @RequestMapping(method = RequestMethod.POST, value = "/order")
    public BitfinexCreatedOrderDto createNewOrder(@RequestBody BitfinexOrderDto orderDto) {
        return orderManager.createNewOrder(orderDto, BitfinexParams.NEW_ORDER.getParams());
    }


    @RequestMapping(method = RequestMethod.POST, value = "/cancel-order")
    public boolean cancelOrder(@RequestParam String orderId) {
        return orderManager.cancelOrder(orderId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cancel-allorders")
    public BitfinexCancelAllOrders cancelMultipleOrders() {
        return orderManager.cancelAllOrders();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/orders")
    public BitfinexCreatedOrderDto[] getActiveOrders() {
        return orderManager.getActiveOrders();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/orders-history")
    public BitfinexCreatedOrderDto[] getOrdersHistory() {
        return orderManager.getOrdersHistory();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/order-status")
    public BitfinexCreatedOrderDto getOrderStatus(@RequestParam String orderId) {
        return orderManager.getOrdersStatus(orderId);
    }


}
