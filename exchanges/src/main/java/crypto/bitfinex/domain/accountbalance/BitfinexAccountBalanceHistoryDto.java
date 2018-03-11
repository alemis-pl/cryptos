package crypto.bitfinex.domain.accountbalance;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BitfinexAccountBalanceHistoryDto {

    private String currency;
    private BigDecimal amount;
    private BigDecimal balance;
    private String description;
    private String timestamp;
}
