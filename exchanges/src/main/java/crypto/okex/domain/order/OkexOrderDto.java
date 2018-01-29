package crypto.okex.domain.order;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OkexOrderDto {

    private String symbol;
    private String type;
    private BigDecimal price;
    private BigDecimal amount;

}
