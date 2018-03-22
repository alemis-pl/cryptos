package crypto.bittrex.domain.accountbalance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BittrexCurrencyBalanceDto {

    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("Balance")
    private String balance;

    @JsonProperty("Available")
    private String available;

    @JsonProperty("Pending")
    private String pending;

    @JsonProperty("CryptoAddress")
    private String cryptoAddress;
}
