package crypto.oanda.domain.positions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OandaPositionDetails {

    private BigDecimal pl;
    private BigDecimal resettablePL;
    private BigDecimal units;
    private BigDecimal unrealizedPL;
}
