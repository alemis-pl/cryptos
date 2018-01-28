package crypto.bitfinex.domain.pasttrades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@ToString
public class PastTradesDto {

    // Using underscore ("_") in variables name is necessary to read correctly response from exchange
    // without creating completed objects converters.
    // I'm breaking the naming convention rule as well as Java 9 assumptions.

    private BigDecimal price;
    private BigDecimal amount;
    private Long timestamp;
    private String exchange;
    private String type;
    private String fee_currency;
    private BigDecimal fee_amount;
    private Long tid;
    private Long order_id;
}
