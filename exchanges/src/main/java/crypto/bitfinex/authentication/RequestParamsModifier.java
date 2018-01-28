package crypto.bitfinex.authentication;

import crypto.bitfinex.domain.params.ParamsModerator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Component
public class RequestParamsModifier {

    private static final String BALANCE_HISTORY = "BALANCE_HISTORY";
    private static final String BALANCE_HISTORY_SINCE = "BALANCE_HISTORY_SINCE";
    private static final String BALANCE_HISTORY_SINCE_WALLET ="BALANCE_HISTORY_SINCE_WALLET";
    private static final String BALANCE_HISTORY_SINCE_UNTIL_WALLET ="BALANCE_HISTORY_SINCE_UNTIL_WALLET";
    private static final String PAST_TRADES = "PAST_TRADES";
    private static final String NEW_ORDER = "NEW_ORDER";
    private static final String ORDER_BY_ID = "ORDER_BY_ID";
    private static final String CANCEL_ALL_ORDERS = "CANCEL_ALL_ORDERS";


    public Map<String, Object> modifyRequestParamMap(ParamsModerator paramsModerator) {
        HashMap<String, Object> createRequestParam = new HashMap<>();
        String paramsType = paramsModerator.getParamType();
        switch(paramsType) {
            case BALANCE_HISTORY:
                createRequestParam = createReqeustParamForBalanceHistory(paramsModerator);
                break;
            case PAST_TRADES:
                createRequestParam = createRequestParam(paramsModerator);
                break;
            case NEW_ORDER:
                createRequestParam = createRequestParamsForNewOrder(paramsModerator);
                break;
            case ORDER_BY_ID:
                createRequestParam = setOrderIdAsParam(paramsModerator.getParamsToSearch().getOrderId());
                break;
            case CANCEL_ALL_ORDERS:
                return createRequestParam;
        }
        return createRequestParam;
    }

    private HashMap<String, Object> createRequestParam(ParamsModerator paramsModerator) {
        HashMap<String, Object> createRequestParam = new HashMap<>();
        createRequestParam.put("symbol", paramsModerator.getParamsToSearch().getCurrency());
        createRequestParam.put("timestamp", paramsModerator.getParamsToSearch().getSinceTimestamp().toString());
        return createRequestParam;
    }

    private HashMap<String, Object> createReqeustParamForBalanceHistory(ParamsModerator paramsModerator) {
        HashMap<String, Object> createRequestParam = new HashMap<>();
        createRequestParam.put("currency", paramsModerator.getParamsToSearch().getCurrency());
        createRequestParam.put("since", paramsModerator.getParamsToSearch().getSinceTimestamp().toString());
        switch(paramsModerator.getParamType()) {
            case BALANCE_HISTORY_SINCE:
                return createRequestParam;
            case BALANCE_HISTORY_SINCE_WALLET:
                createRequestParam.put("wallet", paramsModerator.getParamsToSearch().getWallet());
                return createRequestParam;
            case BALANCE_HISTORY_SINCE_UNTIL_WALLET:
                createRequestParam.put("until", paramsModerator.getParamsToSearch().getUntilTimestamp().toString());
                createRequestParam.put("wallet", paramsModerator.getParamsToSearch().getWallet());
                return createRequestParam;
        }
        return createRequestParam;
    }

    private HashMap<String,Object> createRequestParamsForNewOrder(ParamsModerator paramsModerator) {
        System.out.println(paramsModerator.getOrderDto());
        HashMap<String, Object> createRequestParam = new HashMap<>();
        createRequestParam.put("symbol", paramsModerator.getOrderDto().getSymbol().toLowerCase());
        createRequestParam.put("amount", checkAmount(paramsModerator.getOrderDto().getAmount()));
        createRequestParam.put("price", checkPrice(paramsModerator.getOrderDto().getPrice()));
        createRequestParam.put("side",  paramsModerator.getOrderDto().getSide().toLowerCase());
        createRequestParam.put("type", paramsModerator.getOrderDto().getType().toLowerCase());
        createRequestParam.put("exchange", paramsModerator.getOrderDto().getExchange().toLowerCase());
//        createRequestParam.put("ocoorder", String.valueOf(paramsModerator.getOrderDto().isOcoOrder()));
//
//        if(paramsModerator.getOrderDto().isOcoOrder()) {
//            if(paramsModerator.getOrderDto().getSide().toLowerCase().equals("buy")) {
//                createRequestParam.put("buy_price_oco", paramsModerator.getOrderDto().getOcoStopOrder().toString());
//            }else if (paramsModerator.getOrderDto().getSide().toLowerCase().equals("sell")) {
//                createRequestParam.put("sell_price_oco", paramsModerator.getOrderDto().getOcoStopOrder().toString() );
//            }
//        }
        createRequestParam.entrySet().stream().forEach(System.out::println);
        return  createRequestParam;
    }

    private String checkPrice(BigDecimal price) {
        DecimalFormat decimalFormat = new DecimalFormat("#.########");
        return decimalFormat.format(price);

    }

    private String checkAmount(BigDecimal amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#.########");
        return decimalFormat.format(amount);
    }

    private HashMap<String,Object> setOrderIdAsParam(String orderId) {
        HashMap<String, Object> createRequestParam = new HashMap<>();
        createRequestParam.put("order_id", Long.parseLong(orderId));
        return createRequestParam;
    }
}
