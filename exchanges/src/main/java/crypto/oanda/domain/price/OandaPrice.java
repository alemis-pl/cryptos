package crypto.oanda.domain.price;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OandaPrice {

    private String instrument;
    @JsonDeserialize(using = OandaStringDateTimeDeserializer.class)
    private DateTime time;
    private List<OandaBid> bids;
    private List<OandaAsk> asks;
    private BigDecimal closeoutAsk;
    private BigDecimal closeoutBid;
}
