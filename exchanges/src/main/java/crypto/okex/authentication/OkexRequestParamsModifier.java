package crypto.okex.authentication;

import crypto.okex.domain.params.OkexParamsModerator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Component
public class OkexRequestParamsModifier {

    private static final String WITHOUT_PARAMS = "WITHOUT_PARAMS";
    private static final String NEW_ORDER = "NEW_ORDER";
    private static final String ORDER_BY_ID = "ORDER_BY_ID";

    public Map<String, String> modifyRequestParamMap(OkexParamsModerator paramsModerator) {
        HashMap<String, String> createRequestParam = new HashMap<>();
        String paramsType = paramsModerator.getParamsType();
        switch(paramsType) {
            case WITHOUT_PARAMS:
                return createRequestParam;
            case NEW_ORDER:
                createRequestParam = createRequestParamsForNewOrder(paramsModerator);
                break;
            case ORDER_BY_ID:
                createRequestParam = createRequestParamsForCancelOrder(paramsModerator);
                break;
        }
        return createRequestParam;
    }

    private HashMap<String,String> createRequestParamsForCancelOrder(OkexParamsModerator paramsModerator) {
        HashMap<String, String> createRequestParam = new HashMap<>();
        createRequestParam.put("symbol", paramsModerator.getOrderDto().getSymbol());
        createRequestParam.put("order_id", paramsModerator.getOkexCanceldOrderDto().getOrderId().toString());
        return createRequestParam;
    }

    private HashMap<String,String> createRequestParamsForNewOrder(OkexParamsModerator paramsModerator) {
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
