package crypto.oanda.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import crypto.oanda.domain.positions.OandaPositionsList;
import crypto.oanda.domain.trade.OandaTradesList;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OandaAccount {

    private BigDecimal NAV;
    private String alias;
    private BigDecimal balance;
    private String currency;
    private boolean hedgingEnabled;
    private String id;
    private BigDecimal marginAvailable;
    private BigDecimal marginCloseoutMarginUsed;
    private BigDecimal marginCloseoutNAV;
    private BigDecimal marginCloseoutPercent;
    private BigDecimal marginCloseoutPositionValue;
    private BigDecimal marginCloseoutUnrealizedPL;
    private int openPositionCount;
    private int openTradeCount;
    private int pendingOrderCount;
    private BigDecimal pl;
    private BigDecimal positionValue;
    private OandaPositionsList positions;
    private BigDecimal resettablePL;
    private OandaTradesList trades;
    private BigDecimal unrealizedL;
    private BigDecimal withdrawlLimit;

}
