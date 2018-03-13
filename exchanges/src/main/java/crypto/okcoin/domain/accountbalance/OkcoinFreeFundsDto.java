package crypto.okcoin.domain.accountbalance;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OkcoinFreeFundsDto {

    private BigDecimal xmr;
    private BigDecimal bcc;
    private BigDecimal bch;
    private BigDecimal btc;
    private BigDecimal etc;
    private BigDecimal eth;
    private BigDecimal dash;
    private BigDecimal ltc;
}
