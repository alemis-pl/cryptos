package crypto.oanda.domain.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OandaOrderDetails {
    private double price;
    private OandaOrder.OandaTimeInForce timeInForce;

    public OandaOrderDetails(double price) {
        this.price = price;
    }
}
