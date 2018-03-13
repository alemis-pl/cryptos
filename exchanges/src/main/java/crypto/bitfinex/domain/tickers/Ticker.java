package crypto.bitfinex.domain.tickers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticker {

    private BigDecimal mid;
    private BigDecimal bid;
    private BigDecimal ask;
    private BigDecimal last_price;
    private BigDecimal low;
    private BigDecimal high;
    private BigDecimal volume;
    private String timestamp;
}
