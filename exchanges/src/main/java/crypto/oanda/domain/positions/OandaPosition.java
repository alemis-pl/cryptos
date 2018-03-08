package crypto.oanda.domain.positions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OandaPosition {

    private String instrument;
    private BigDecimal pl;
    private BigDecimal resettablePL;
    private BigDecimal unrealizedPL;
    @JsonProperty(value = "long")
    private OandaPositionDetails longPosition;
    @JsonProperty(value = "short")
    private OandaPositionDetails shortPosition;


}
