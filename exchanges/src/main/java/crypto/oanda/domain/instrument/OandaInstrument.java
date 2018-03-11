package crypto.oanda.domain.instrument;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OandaInstrument {

    private String displayName;
    private int displayPrecision;
    private BigDecimal marginRate;
    private BigDecimal maximumOrderUnits;
    private BigDecimal maximumPositionSize;
    private BigDecimal maximumTrailingStopDistance;
    private BigDecimal minimumTradeSize;
    private BigDecimal minimumTrailingStopDistance;
    private String name;
    private BigDecimal pipLocation;
    private BigDecimal tradeUnitsPrecision;
    private String type;
}
