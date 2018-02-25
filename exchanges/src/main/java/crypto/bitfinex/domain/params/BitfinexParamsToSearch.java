package crypto.bitfinex.domain.params;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class BitfinexParamsToSearch {

    private String currency;
    private Long sinceTimestamp;
    private Long untilTimestamp;
    private String wallet;
    private String orderId;

    public BitfinexParamsToSearch(String orderId) {
        this.orderId = orderId;
    }

    public BitfinexParamsToSearch(String currency, Long sinceTimestamp, Long untilTimestamp, String wallet) {
        this.currency = currency;
        this.sinceTimestamp = sinceTimestamp;
        this.untilTimestamp = untilTimestamp;
        this.wallet = wallet;
    }

    public BitfinexParamsToSearch(String currency, Long sinceTimestamp, String wallet) {
        this.currency = currency;
        this.sinceTimestamp = sinceTimestamp;
        this.wallet = wallet;
    }

    public BitfinexParamsToSearch(String currency, Long sinceTimestamp) {
        this.currency = currency;
        this.sinceTimestamp = sinceTimestamp;
    }
}
