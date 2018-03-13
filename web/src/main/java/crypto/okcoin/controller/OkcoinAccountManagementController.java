package crypto.okcoin.controller;


import crypto.okcoin.client.*;
import crypto.okcoin.domain.accountbalance.OkcoinAccountInfoDto;
import crypto.okcoin.domain.accountrecords.OkcoinAccountRecordsListDto;
import crypto.okcoin.domain.accountrecords.OkcoinAccountRecordsRequestDto;
import crypto.okcoin.domain.order.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("v1/crypto")
public class OkcoinAccountManagementController {

    @Autowired
    private OkcoinAccountInfoClient accountInfoClient;

    @Autowired
    private OkcoinNewOrderClient newOrderClient;

    @Autowired
    private OkcoinCancelOrderClient cancelOrderClient;

    @Autowired
    private OkcoinOrderInfoClient orderInfoClient;

    @Autowired
    private OkcoinOrdersHistoryClient ordersHistoryClient;

    @Autowired
    private OkcoinAccountRecordsHistoryClient accountRecordsHistoryClient;

    @RequestMapping(method = RequestMethod.POST, value = "/okex_account_info")
    private OkcoinAccountInfoDto getAccountInfo() {
        return accountInfoClient.getAccountInfo();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/okex_new_order")
    private OkcoinCreatedOrderDto placeNewOrder(@RequestBody OkcoinOrderDto orderDto) {
        return newOrderClient.placeNewOrder(orderDto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/okex_cancel_order")
    private OkcoinCanceldOrderResponseDto cancelOrder(@RequestBody OkcoinOrderById orderById) {
        return cancelOrderClient.cancelOrder(orderById);
    }

    @RequestMapping(method = RequestMethod.POST, value = "okex_orders_info")
    private OkcoinOrdersInfoDto getOrdersInfo(@RequestBody OkcoinOrderById orderById) {
        return orderInfoClient.getOrdersInfo(orderById);
    }

    @RequestMapping(method = RequestMethod.POST, value = "okex_orders_history")
    private OkcoinOrdersHistoryDto getOrdersHistory(@RequestBody OkcoinOrderHistoryRequest okexOrderHistoryRequest) {
        return ordersHistoryClient.getOrdersHistory(okexOrderHistoryRequest);
    }

    @RequestMapping(method = RequestMethod.POST, value = "okex_account_records")
    private OkcoinAccountRecordsListDto getAccountRecords(@RequestBody OkcoinAccountRecordsRequestDto okexAccountRecordsRequestDto) {
        return accountRecordsHistoryClient.getAccountHistory(okexAccountRecordsRequestDto);
    }
}
