package crypto.okex.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class OkexOrderHistoryDto {

    private BigDecimal amount;
    private BigDecimal avg_price;
    private Long create_date;
    private Long order_id;
    private BigDecimal price;
    private String status;
    private String symbol;
    private String type;
}
