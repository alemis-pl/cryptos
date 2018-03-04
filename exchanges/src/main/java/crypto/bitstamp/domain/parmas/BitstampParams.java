package crypto.bitstamp.domain.parmas;

import lombok.Getter;

@Getter
public enum  BitstampParams {

    WITHOUT_PARAMS("WITHOUT_PARAMS");

    private String params;

    BitstampParams(final String params) {
        this.params = params;
    }
}
