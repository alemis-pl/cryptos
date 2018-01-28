package crypto.bitfinex.client;

import com.google.gson.Gson;
import crypto.bitfinex.authentication.ExchangeAuthentication;
import crypto.bitfinex.authentication.ExchangeConnectionExceptions;
import crypto.bitfinex.authentication.ExchangeHttpResponse;
import crypto.bitfinex.domain.params.Params;
import crypto.bitfinex.domain.params.ParamsModerator;
import crypto.bitfinex.domain.params.ParamsToSearch;
import crypto.bitfinex.domain.positions.ActivePositionsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ActivePositionsClient {

    @Value("${bitfinex.positions.active}")
    private String activePositions;

    @Autowired
    private ExchangeAuthentication exchangeAuthentication;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderStatusClient.class);

    private static final String POST = "POST";

    public ActivePositionsDto[] getActivePositions() throws Exception {

        ParamsToSearch paramsToSearch = null;
        ParamsModerator paramsModerator = new ParamsModerator(Params.WITHOUT_PARAMS.getParams(), paramsToSearch);

        try {
            ExchangeHttpResponse exchangeHttpResponse = exchangeAuthentication.sendExchangeRequest(activePositions, POST, paramsModerator);
            LOGGER.info("Active positions information: " + exchangeHttpResponse);

            Gson gson = new Gson();

            return gson.fromJson(exchangeHttpResponse.getContent(), ActivePositionsDto[].class);
        } catch (IOException e) {
            LOGGER.error(ExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
            throw new IOException(ExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
        } catch (NullPointerException e) {
            LOGGER.error(ExchangeConnectionExceptions.ACTIVE_POSITIONS_ERROR.getException(), e);
            throw new IOException(ExchangeConnectionExceptions.ACTIVE_POSITIONS_ERROR.getException(), e);
        }
    }
}
