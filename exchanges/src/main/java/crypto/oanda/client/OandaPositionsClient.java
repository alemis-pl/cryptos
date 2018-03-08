package crypto.oanda.client;

import crypto.oanda.authentication.OandaAuthentication;
import crypto.oanda.authentication.OandaRequestType;
import crypto.oanda.authentication.OandaUrlCreator;
import crypto.oanda.authentication.OandaUrlType;
import crypto.oanda.domain.positions.OandaPosition;
import crypto.oanda.domain.positions.OandaPositionsList;
import crypto.persistance.apikey.ApiKeys;
import crypto.persistance.service.DbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class OandaPositionsClient {

    private OandaAuthentication authentication;
    private OandaUrlCreator urlCreation;
    private DbService dbService;

    @Autowired
    public OandaPositionsClient(OandaAuthentication authentication, OandaUrlCreator urlCreation, DbService dbService) {
        this.authentication = authentication;
        this.urlCreation = urlCreation;
        this.dbService = dbService;
    }

    private ApiKeys getApiKeys() {
        return dbService.getApiKeysByExchange("oanda");
    }

    private OandaPositionsList getAllPositions() {
        ApiKeys userData = getApiKeys();
        HttpEntity entity = authentication.createHeaders(userData.getApiKey(), userData.getClientId(), OandaRequestType.STANDARD_REQUEST.getRequestType());
        String url = urlCreation.createUrl(userData.getApiKey(), userData.getClientId(), OandaUrlType.POSITIONS.getUrlType());
        Optional<OandaPositionsList> positions  = authentication.getResponse(url, entity, OandaPositionsList.class, HttpMethod.GET);
        positions.get().forEach(System.out::println);
        return positions.orElse(new OandaPositionsList());
    }

    private OandaPositionsList getOpenPositions() {
        ApiKeys userData = getApiKeys();
        HttpEntity entity = authentication.createHeaders(userData.getApiKey(), userData.getClientId(), OandaRequestType.STANDARD_REQUEST.getRequestType());
        String url = urlCreation.createUrl(userData.getApiKey(), userData.getClientId(), OandaUrlType.POSITIONS_OPEN.getUrlType());
        Optional<OandaPositionsList> positions = authentication.getResponse(url, entity, OandaPositionsList.class, HttpMethod.GET);
        positions.get().forEach(System.out::println);
        return positions.orElse(new OandaPositionsList());
    }

    private OandaPosition getPosition(String instrument) {
        ApiKeys userData = getApiKeys();
        HttpEntity entity = authentication.createHeaders(userData.getApiKey(), userData.getClientId(), OandaRequestType.STANDARD_REQUEST.getRequestType());
        String url = urlCreation.createUrl(userData.getApiKey(), userData.getClientId(), OandaUrlType.POSITIONS.getUrlType());
        Optional<OandaPosition> position = authentication.getResponse(url, entity, OandaPosition.class, HttpMethod.GET);
        System.out.println(position.get());
        return position.orElse(new OandaPosition());
    }

}
