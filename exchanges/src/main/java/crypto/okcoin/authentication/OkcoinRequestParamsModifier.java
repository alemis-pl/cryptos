package crypto.okcoin.authentication;

import crypto.okcoin.domain.params.OkcoinParamsModerator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Component
public class OkcoinRequestParamsModifier {

    private static final String WITHOUT_PARAMS = "WITHOUT_PARAMS";
    private static final String NEW_ORDER = "NEW_ORDER";
    private static final String ORDER_BY_ID = "ORDER_BY_ID";
    private static final String ORDERS_HISTORY = "ORDERS_HISTORY";
    private static final String ACCOUNT_RECORDS = "ACCOUNT_RECORDS";

    public Map<String, String> modifyRequestParamMap(OkcoinParamsModerator paramsModerator) {
        HashMap<String, String> createRequestParam = new HashMap<>();
        String paramsType = paramsModerator.getParamsType();
        switch(paramsType) {
            case WITHOUT_PARAMS:
                return createRequestParam;
            case NEW_ORDER:
                createRequestParam = createRequestParamsForNewOrder(paramsModerator);
                break;
            case ORDER_BY_ID:
                createRequestParam = createRequestParamsForManagingOrder(paramsModerator);
                break;
            case ORDERS_HISTORY:
                createRequestParam = createRequestParamsForOrdersHistory(paramsModerator);
                break;
            case ACCOUNT_RECORDS:
                createRequestParam = createRequestParamsForAccountRecords(paramsModerator);
        }
        return createRequestParam;
    }

    private HashMap<String,String> createRequestParamsForAccountRecords(OkcoinParamsModerator paramsModerator) {
        HashMap<String, String> createRequestParam = new HashMap<>();
        createRequestParam.put("symbol", paramsModerator.getOkexAccountRecordsRequestDto().getSymbol());
        createRequestParam.put("type", paramsModerator.getOkexAccountRecordsRequestDto().getType());
        createRequestParam.put("current_page", paramsModerator.getOkexAccountRecordsRequestDto().getCurrentPage());
        createRequestParam.put("page_length", paramsModerator.getOkexAccountRecordsRequestDto().getPageLength());
        return createRequestParam;
    }

    private HashMap<String,String> createRequestParamsForOrdersHistory(OkcoinParamsModerator paramsModerator) {
        HashMap<String, String> createRequestParam = new HashMap<>();
        createRequestParam.put("symbol", paramsModerator.getOkexOrderHistoryRequest().getSymbol());
        createRequestParam.put("status", paramsModerator.getOkexOrderHistoryRequest().getStatus());
        createRequestParam.put("current_page", paramsModerator.getOkexOrderHistoryRequest().getCurrentPage());
        createRequestParam.put("page_length", paramsModerator.getOkexOrderHistoryRequest().getPageLength());
        return createRequestParam;
    }

    private HashMap<String,String> createRequestParamsForManagingOrder(OkcoinParamsModerator paramsModerator) {
        HashMap<String, String> createRequestParam = new HashMap<>();
        createRequestParam.put("symbol", paramsModerator.getOkexCanceldOrderDto().getSymbol());
        createRequestParam.put("order_id", paramsModerator.getOkexCanceldOrderDto().getOrderId().toString());
        return createRequestParam;
    }

    private HashMap<String,String> createRequestParamsForNewOrder(OkcoinParamsModerator paramsModerator) {
        HashMap<String, String> createRequestParam = new HashMap<>();
        createRequestParam.put("symbol", paramsModerator.getOrderDto().getSymbol());
        createRequestParam.put("type", paramsModerator.getOrderDto().getType());
        createRequestParam.put("price", checkPrice(paramsModerator.getOrderDto().getPrice()));
        createRequestParam.put("amount", checkAmount(paramsModerator.getOrderDto().getAmount()));
        return createRequestParam;
    }

    private String checkPrice(BigDecimal price) {
        DecimalFormat decimalFormat = new DecimalFormat("#.########");
        return decimalFormat.format(price);

    }

    private String checkAmount(BigDecimal amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#.########");
        return decimalFormat.format(amount);
    }
}
