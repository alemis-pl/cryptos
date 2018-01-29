package crypto.bitfinex.authentication;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BitfinexExchangeHttpResponse {

    private int statusCode;
    private String responseMessage;
    private String content;
}
