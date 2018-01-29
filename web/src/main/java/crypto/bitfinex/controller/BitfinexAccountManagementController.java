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
import crypto.bitfinex.facade.BitfinexOrderFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("v1/crypto")
public class BitfinexAccountManagementController {

    @Autowired
    private BitfinexAccountBalanceClient accountBalanceClient;

    @Autowired
    private BitfinexAccountBalanceHistoryClient accountBalanceHistoryClient;

    @Autowired
    private BitfinexOrderFacade orderFacade;

    @Autowired
    private BitfinexPastTradesClient pastTradesClient;

    @Autowired
    private BitfinexCancelOrderClient cancelOrderClient;

    @Autowired
    private BitfinexCancelAllOrdersClient cancelAllOrdersClient;

    @Autowired
    private BitfinexActiveOrdersClient activeOrdersClients;

    @Autowired
    private BitfinexOrdersHistoryClient ordersHistoryClient;

    @Autowired
    private BitfinexOrderStatusClient orderStatusClient;

    @Autowired
    private BitfinexActivePositionsClient activePositionsClient;

    @RequestMapping(method = RequestMethod.GET, value = "/balance")
    private BitfinexAccountBalanceListDto getUserAccountBalance() throws Exception {
        return accountBalanceClient.getAccountBalance(BitfinexParams.WITHOUT_PARAMS.getParams());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/balance-history")
    private BitfinexAccountBalanceHistoryListDto getUserAccountBalanceHistory(@RequestParam String currency,
                                                                              @RequestParam(required = false) Long since,
                                                                              @RequestParam(required = false) Long until,
                                                                              @RequestParam(required = false) String wallet) throws Exception {

        BitfinexParamsToSearch paramsToSearch = new BitfinexParamsToSearch(currency, since, until, wallet);
        return accountBalanceHistoryClient.getAccountBalanceHistory(paramsToSearch, BitfinexParams.BALANCE_HISTORY.getParams());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/order")
    public BitfinexCreatedOrderDto createNewOrder(@RequestBody BitfinexOrderDto orderDto) throws Exception {
        return orderFacade.createNewOrder(orderDto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/past-trades")
    public BitfinexPastTradesListDto getPastTrades(@RequestParam String symbol, @RequestParam(required = false) String sinceTimestamp) throws Exception {
        return pastTradesClient.getPastTrades(symbol, sinceTimestamp);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cancel-order")
    public boolean cancelOrder(@RequestParam String orderId) throws Exception {
        return cancelOrderClient.cancelOrder(orderId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cancel-allorders")
    public BitfinexCancelAllOrders cancelMultipleOrders() throws Exception {
        return cancelAllOrdersClient.cancelAllOrders();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/orders")
    public BitfinexCreatedOrderDto[] getActiveOrders() throws Exception {
        return activeOrdersClients.getActiveOrders();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/orders-history")
    public BitfinexCreatedOrderDto[] getOrdersHistory() throws Exception {
        return ordersHistoryClient.getOrdersHistory();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/order-status")
    public BitfinexCreatedOrderDto getOrderStatus(@RequestParam String orderId) throws Exception {
        return orderStatusClient.getOrdersStatus(orderId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/positions")
    public BitfinexActivePositionsDto[] getActivePositions() throws Exception {
        return activePositionsClient.getActivePositions();
    }
}
