package crypto.bittrex.domain.ticker;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BittrexTickerResponse {

    private String currency;
    private BittrexTickerPrices prices;
}
