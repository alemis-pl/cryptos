package crypto.bittrex.domain.order;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BittrexOrderDto {

    private boolean success;
    private String message;
    private BittrexOrderInfo result;
}
