package crypto.oanda.domain.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OandaOrderDetails {

    private BigDecimal price;
    private OandaOrder.OandaTimeInForce timeInForce;

    public OandaOrderDetails(BigDecimal price) {
        this.price = price;
    }
}
