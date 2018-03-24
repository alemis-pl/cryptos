package crypto.bittrex.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BittrexOrderPlacedResponse {

    private boolean success;
    private String message;
    private BittrexOrderUuid result;
}
