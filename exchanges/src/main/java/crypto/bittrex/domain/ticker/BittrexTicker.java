package crypto.bittrex.domain.ticker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BittrexTicker {

    private boolean success;
    private String message;
    private BittrexTickerPrices result;
}
