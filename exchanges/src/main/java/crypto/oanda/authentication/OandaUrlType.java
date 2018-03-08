package crypto.oanda.authentication;

import lombok.Getter;

@Getter
public enum OandaUrlType {

    PRICES("PRICES"),
    POSITIONS("POSITIONS"),
    POSITIONS_OPEN("POSITIONS_OPEN");

    private String urlType;

    OandaUrlType(String urlType) {
        this.urlType = urlType;
    }
}
