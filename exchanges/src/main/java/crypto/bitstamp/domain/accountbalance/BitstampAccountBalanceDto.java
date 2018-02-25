package crypto.bitstamp.domain.accountbalance;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BitstampAccountBalanceDto {

    private String currency;
    private BigDecimal balance;
}
