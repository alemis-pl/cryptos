package crypto.bitfinex.domain.order;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreatedOrderDto {

    // Using underscore ("_") in variables name is necessary to read correctly response from exchange
    // without creating completed objects converters.
    // I'm breaking the naming convention rule as well as Java 9 assumptions.

    private Long id;
    private Long cid;
    private String cid_date;
    private String gid;
    private String symbol;
    private String exchange;
    private BigDecimal price;
    private BigDecimal avg_execution_price;
    private String side;
    private String type;
    private BigDecimal timestamp;
    private Boolean is_live;
    private Boolean is_cancelled;
    private Boolean is_hidden;
    private String oco_order;
    private Boolean was_forced;
    private BigDecimal original_amount;
    private BigDecimal remaining_amount;
    private BigDecimal executed_amount;
    private String src;
    private Long order_id;
}
