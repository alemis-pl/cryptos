package crypto.authentication_help;

import lombok.Getter;

@Getter
public enum HmacExceptionsMsg {

    ENCODING_ERROR("Failed to encode secret key. The Character Encoding is not supported."),
    INVALID_KEY_ERROR("Something wrong with keys - invalid encoding, wrong length etc."),
    LACK_OF_ALGORITHM_ERROR("HmacSHA384 not available in the environment");

    private String exception;

    HmacExceptionsMsg(final String exception) {
        this.exception = exception;
    }
}
