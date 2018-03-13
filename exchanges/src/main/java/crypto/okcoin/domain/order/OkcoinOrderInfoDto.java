package crypto.okcoin.domain.order;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OkcoinOrderInfoDto {

    private BigDecimal amount;
    private BigDecimal avg_price;
    private Long create_date;
    private BigDecimal deal_amount;
    private Long order_id;
    private Long orders_id;
    private BigDecimal price;
    private Integer status;
    private String symbol;
    private String type;
}
