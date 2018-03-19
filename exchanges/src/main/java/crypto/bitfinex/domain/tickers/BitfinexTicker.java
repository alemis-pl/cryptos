package crypto.bitfinex.domain.tickers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitfinexTicker {

    private BigDecimal mid;
    private BigDecimal bid;
    private BigDecimal ask;
    private BigDecimal last_price;
    private BigDecimal low;
    private BigDecimal high;
    private BigDecimal volume;
    private String timestamp;
}
