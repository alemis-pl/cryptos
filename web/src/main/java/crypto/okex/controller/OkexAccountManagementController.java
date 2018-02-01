package crypto.okex.controller;


import crypto.okex.client.*;
import crypto.okex.domain.accountbalance.OkexAccountInfoDto;
import crypto.okex.domain.accountrecords.OkexAccountRecordsListDto;
import crypto.okex.domain.accountrecords.OkexAccountRecordsRequestDto;
import crypto.okex.domain.order.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("v1/crypto")
public class OkexAccountManagementController {

    @Autowired
    private OkexAccountInfoClient accountInfoClient;

    @Autowired
    private OkexNewOrderClient newOrderClient;

    @Autowired
    private OkexCancelOrderClient cancelOrderClient;

    @Autowired
    private OkexOrderInfoClient orderInfoClient;

    @Autowired
    private OkexOrdersHistoryClient ordersHistoryClient;

    @Autowired
    private OkexAccountRecordsHistoryClient accountRecordsHistoryClient;

    @RequestMapping(method = RequestMethod.POST, value = "/okex_account_info")
    private OkexAccountInfoDto getAccountInfo() {
        return accountInfoClient.getAccountInfo();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/okex_new_order")
    private OkexCreatedOrderDto placeNewOrder(@RequestBody OkexOrderDto orderDto) {
        return newOrderClient.placeNewOrder(orderDto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/okex_cancel_order")
    private OkexCanceldOrderResponseDto cancelOrder(@RequestBody OkexOrderById orderById) {
        return cancelOrderClient.cancelOrder(orderById);
    }

    @RequestMapping(method = RequestMethod.POST, value = "okex_orders_info")
    private OkexOrdersInfoDto getOrdersInfo(@RequestBody OkexOrderById orderById) {
        return orderInfoClient.getOrdersInfo(orderById);
    }

    @RequestMapping(method = RequestMethod.POST, value = "okex_orders_history")
    private OkexOrdersHistoryDto getOrdersHistory(@RequestBody OkexOrderHistoryRequest okexOrderHistoryRequest) {
        return ordersHistoryClient.getOrdersHistory(okexOrderHistoryRequest);
    }

    @RequestMapping(method = RequestMethod.POST, value = "okex_account_records")
    private OkexAccountRecordsListDto getAccountRecords(@RequestBody OkexAccountRecordsRequestDto okexAccountRecordsRequestDto) {
        return accountRecordsHistoryClient.getAccountHistory(okexAccountRecordsRequestDto);
    }
}
