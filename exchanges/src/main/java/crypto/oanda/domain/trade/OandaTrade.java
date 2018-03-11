package crypto.oanda.domain.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OandaTrade {

    private Long id;
    private String instrument;
    private BigDecimal price;
    private DateTime openTime;
    private TradeState state;
    private BigDecimal initialUnits;
    private BigDecimal currentUnits;
    private BigDecimal realizedPL;
    private BigDecimal unrealizedPL;
    private BigDecimal averageClosePrice;
    private DateTime closeTime;


    public enum TradeState {
        OPEN, CLOSED, CLOSE_WHEN_TRADEABLE;
    }
}
