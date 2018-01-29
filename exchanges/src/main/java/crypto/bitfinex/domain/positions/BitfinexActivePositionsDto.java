package crypto.bitfinex.domain.positions;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BitfinexActivePositionsDto {

    private Long id;
    private String symbol;
    private String status;
    private BigDecimal base;
    private BigDecimal amount;
    private Long timestamp;
    private BigDecimal swap;
    private BigDecimal pl;
}
