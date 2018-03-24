package crypto.bittrex.domain.ticker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BittrexTickerPrices {

    @JsonProperty(value = "Bid")
    private BigDecimal bid;

    @JsonProperty(value = "Ask")
    private BigDecimal ask;

    @JsonProperty(value = "Last")
    private BigDecimal last;
}
