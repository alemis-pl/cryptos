package crypto.bitstamp.authentication;

import lombok.Getter;

@Getter
public enum BitstampExchangeConnectionExceptions {

    UNEXPECTED_IO_ERROR_MSG("Failed to connect to Exchange due to unexpected IO error."),
    IO_SOCKET_TIMEOUT_ERROR_MSG("Failed to connect to Exchange due to socket timeout."),
    CONNECTION_ERROR("Failed to connect to Exchange. Something dead!");

    String exception;

    BitstampExchangeConnectionExceptions(String exception) {
        this.exception = exception;
    }
}
