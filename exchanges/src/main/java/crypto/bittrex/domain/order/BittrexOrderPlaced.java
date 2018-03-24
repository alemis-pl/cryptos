package crypto.bittrex.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BittrexOrderPlaced {

    private String orderType;
    private String currencyPair;
    private BigDecimal quantity;
    private BigDecimal priceLimit;
}
