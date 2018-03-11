package crypto.oanda.domain.order;

import java.math.BigDecimal;

public class OandaTrailingStopLossDetails {
    private BigDecimal distance;
    private OandaOrder.OandaTimeInForce timeInForce;

    public OandaTrailingStopLossDetails(BigDecimal distance) {
        this.distance = distance;
    }
}
