package crypto.bittrex.domain.accountbalance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BittrexAccountBalanceDto {

    private boolean success;
    private String message;
    private BittrexCurrencyBalanceListDto result;

}
