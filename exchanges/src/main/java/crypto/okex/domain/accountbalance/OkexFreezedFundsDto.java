package crypto.okex.domain.accountbalance;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OkexFreezedFundsDto {

    private BigDecimal xmr;
    private BigDecimal bcc;
    private BigDecimal bch;
    private BigDecimal btc;
    private BigDecimal etc;
    private BigDecimal eth;
    private BigDecimal dash;
    private BigDecimal ltc;
}
