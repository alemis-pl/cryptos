package crypto.oanda.client;

import crypto.oanda.authentication.*;
import crypto.oanda.domain.account.OandaAccount;
import crypto.oanda.domain.instrument.OandaInstrumentList;
import crypto.oanda.domain.trade.OandaTradesList;
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
public class OandaAccountManager {

    private OandaAuthentication authentication;
    private OandaUrlCreator urlCreation;
    private DbService dbService;

    @Autowired
    public OandaAccountManager(OandaAuthentication authentication, OandaUrlCreator urlCreation, DbService dbService) {
        this.authentication = authentication;
        this.urlCreation = urlCreation;
        this.dbService = dbService;
    }

    private ApiKeys getApiKeys() {
        return dbService.getApiKeysByExchange("oanda");
    }


    //url Type is ACCOUNT or ACCOUNT_SUMMARY
    private OandaAccount getAccountInfo(String urlType) {
        String token = getApiKeys().getApiKey();
        String clientId = getApiKeys().getClientId();
        OandaHeadersParameters parameters = new OandaHeadersParameters(token, OandaRequestType.STANDARD_REQUEST.getRequestType());
        OandaUrlParameters urlParameters = new OandaUrlParameters(urlType, clientId);
        HttpEntity entity = authentication.createHeaders(parameters);
        String url = urlCreation.createUrl(urlParameters).orElse(new String());
        Optional<OandaAccount> account = authentication.getResponse(url, entity, OandaAccount.class, HttpMethod.GET);
        return account.orElse(new OandaAccount());
    }

    private OandaInstrumentList getTradabelInstruments() {
        String token = getApiKeys().getApiKey();
        String clientId = getApiKeys().getClientId();
        OandaHeadersParameters parameters = new OandaHeadersParameters(token, OandaRequestType.STANDARD_REQUEST.getRequestType());
        OandaUrlParameters urlParameters = new OandaUrlParameters(OandaUrlType.TRADEABLE_INSTRUMENTS.getUrlType(), clientId);
        HttpEntity entity = authentication.createHeaders(parameters);
        String url = urlCreation.createUrl(urlParameters).orElse(new String());
        Optional<OandaInstrumentList> instrumentList = authentication.getResponse(url, entity, OandaInstrumentList.class, HttpMethod.GET);
        return instrumentList.orElse(new OandaInstrumentList());
    }

}
