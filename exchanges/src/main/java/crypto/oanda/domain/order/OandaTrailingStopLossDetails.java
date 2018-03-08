package crypto.oanda.domain.order;

public class OandaTrailingStopLossDetails {
    private double distance;
    private OandaOrder.OandaTimeInForce timeInForce;

    public OandaTrailingStopLossDetails(double distance) {
        this.distance = distance;
    }
}
