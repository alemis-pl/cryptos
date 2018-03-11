package crypto.oanda.authentication;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class OandaUrlParameters {

    private String urlType;
    private String accountId;
    private String instrument;
    private Long orderId;

    public OandaUrlParameters(String urlType, String accountId) {
        this.urlType = urlType;
        this.accountId = accountId;
    }

    public OandaUrlParameters(String urlType, String accountId, String instrument) {
        this.urlType = urlType;
        this.accountId = accountId;
        this.instrument = instrument;
    }

    public OandaUrlParameters(String urlType, String accountId, Long orderId) {
        this.urlType = urlType;
        this.accountId = accountId;
        this.orderId = orderId;
    }
}
