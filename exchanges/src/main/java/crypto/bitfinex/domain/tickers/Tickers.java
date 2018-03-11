package crypto.bitfinex.domain.tickers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Tickers {

    private String currencyPair;
    private Double bid;
    private Double bidSize;
    private Double ask;
    private Double askSize;
    private Double dailyChange;
    private Double dailyChangePerc;
    private Double lastPrice;
    private Double volume;
    private Double high;
    private Double low;
}
