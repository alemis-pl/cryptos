package crypto.bitfinex.domain.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Order {

    private String symbol;
    private BigDecimal amount;
    private BigDecimal price;
    private String side;
    private String type;
    private String exchange;
    private boolean isHidden;
    private boolean isPostonly;
    private boolean ocoOrder;
    private BigDecimal buyPriceOco;
    private BigDecimal sellPriceOco;
}
