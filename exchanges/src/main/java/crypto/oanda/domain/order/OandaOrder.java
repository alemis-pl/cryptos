package crypto.oanda.domain.order;

import crypto.oanda.domain.date.OandaDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class OandaOrder {

    private String id;
    private String replacesOrderID;
    private String createTime;
    private OandaOrderState state;
    private OandaOrderType type;
    private String instrument;
    private Integer units;
    private Double price;
    private Double priceBound;
    private OandaTimeInForce timeInForce;
    private String gtdTime = OandaDateTime.rfc3339Plus2Days();
    private OrderPositionFill positionFill;
    private OrderTriggerCondition triggerCondition;
    private OandaOrderDetails takeProfitOnFill;
    private OandaOrderDetails stopLossOnFill;
    private OandaTrailingStopLossDetails trailingStopLossOnFill;
    private String cancelledTime;

    public enum OandaOrderState {
        PENDING, FILLED, TRIGGERED, CANCELLED
    }

    public enum OandaOrderType {
        MARKET, LIMIT, STOP, MARKET_IF_TOUCHED, TAKE_PROFIT, STOP_LOSS, TRAILING_STOP_LOSS,
        MARKET_ORDER, LIMIT_ORDER, STOP_ORDER, MARKET_IF_TOUCHED_ORDER, TAKE_PROFIT_ORDER, STOP_LOSS_ORDER, TRAILING_STOP_LOSS_ORDER
    }

    public enum OandaTimeInForce {
        GTC, GTD, GFD, FOK, IOC
    }

    public enum OrderPositionFill {
        OPEN_ONLY, REDUCE_FIRST, REDUCE_ONLY, DEFAULT
    }

    public enum OrderTriggerCondition {
        DEFAULT, INVERSE, BID, ASK, MID
    }

}
