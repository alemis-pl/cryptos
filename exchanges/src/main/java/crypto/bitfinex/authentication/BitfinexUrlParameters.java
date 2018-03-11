package crypto.bitfinex.authentication;

import lombok.Getter;

@Getter
public enum BitfinexUrlParameters {

    ACCOUNT_BALANCE("ACCOUNT_BALANCE");

    String urlType;

    BitfinexUrlParameters(String urlType) {
        this.urlType = urlType;
    }
}
