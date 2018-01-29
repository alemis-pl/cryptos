package crypto.bitfinex.domain.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class BitfinexOrderDto {

    private String symbol;
    private BigDecimal amount;
    private BigDecimal price;
    private String side;
    private String type;
    private String exchange;
    private boolean isOcoOrder;
    private BigDecimal ocoStopOrder;
}
