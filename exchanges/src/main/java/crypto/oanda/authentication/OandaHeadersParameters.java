package crypto.oanda.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class OandaHeadersParameters {

    private String token;
    private String requestType;
    private Map<String, Object> parameters;

    public OandaHeadersParameters(String token, String requestType, Map<String, Object> parameters) {
        this.token = token;
        this.requestType = requestType;
        this.parameters = parameters;
    }

    public OandaHeadersParameters(String token, String requestType) {
        this.token = token;
        this.requestType = requestType;
    }
}
