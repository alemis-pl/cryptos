package crypto.okcoin.domain.order;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OkcoinOrderDto {

    private String symbol;
    private String type;
    private BigDecimal price;
    private BigDecimal amount;

}
