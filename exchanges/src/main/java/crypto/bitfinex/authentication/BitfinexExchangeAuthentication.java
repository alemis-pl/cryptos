package crypto.bitfinex.authentication;

import com.google.gson.Gson;
import crypto.apikeys.ApiKeys;
import crypto.apikeys.ApiKeysRepository;
import crypto.authentication_help.HmacEncoder;
import crypto.bitfinex.domain.params.BitfinexParamsModerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import javax.crypto.Mac;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class BitfinexExchangeAuthentication {

    private ApiKeysRepository apiKeysRepository;
    private HmacEncoder hmacEncoder;
    private RestTemplate restTemplate;
    private BitfinexRequestParamsModifier requestParamsModifier;

    public BitfinexExchangeAuthentication( ApiKeysRepository apiKeysRepository, HmacEncoder hmacEncoder, RestTemplate restTemplate, BitfinexRequestParamsModifier requestParamsModifier) {
        this.apiKeysRepository = apiKeysRepository;
        this.hmacEncoder = hmacEncoder;
        this.restTemplate = restTemplate;
        this.requestParamsModifier = requestParamsModifier;
    }

    private long nonce = System.currentTimeMillis();
    private Gson gson = new Gson();
    private Mac mac;

    private ApiKeys getUserApiKeys() {
        return apiKeysRepository.getByExchange("bitfinex");
    }

    public HttpEntity createHttpEntity(BitfinexParamsModerator paramsModerator, String urlPath) {
        ApiKeys apiKeys = getUserApiKeys();
        Map<String, Object> params = createFinalParams(paramsModerator, urlPath);

        HttpEntity entity = null;
        try {
            String payload = gson.toJson(params);
            String payloadBase64 = createPayloadBase64(payload);
            String payloadSha384hmac = hmacEncoder.hmacDigestBitfinex(mac, payloadBase64, apiKeys.getApiSecretKey());

            entity = new HttpEntity(createStandardHeaders(apiKeys, payloadBase64, payloadSha384hmac));

        }catch (IOException e ) {
            log.error(BitfinexExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
        }
        return entity;
    }

    private HttpHeaders createStandardHeaders(ApiKeys apiKeys, String payloadBase64, String payloadSha384hmac) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        headers.set("X-BFX-APIKEY", apiKeys.getApiKey());
        headers.set("X-BFX-PAYLOAD", payloadBase64);
        headers.set("X-BFX-SIGNATURE", payloadSha384hmac);
        return headers;
    }


    private Map<String, Object> createFinalParams(BitfinexParamsModerator paramsModerator, String urlPath) {
        Map<String, Object> params = requestParamsModifier.modifyRequestParamMap(paramsModerator);
        System.out.println(Long.toString(getNonce()));
        params.put("request", urlPath);
        params.put("nonce", Long.toString(getNonce()));
        return params;
    }

    public <T> Optional<T> getResponse(String url, HttpEntity<?> entity, Class<T> typeOfResponse, HttpMethod httpMethod) {
        T response = null;

        try {
            if (HttpMethod.POST.equals(httpMethod)) {
                restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
                response = restTemplate.postForObject(url, entity, typeOfResponse);
            }
            ResponseEntity<T> responseEntity = restTemplate.exchange(url, httpMethod, entity, typeOfResponse);
            if(responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.hasBody()) {
                response = responseEntity.getBody();
            }
        }catch (HttpStatusCodeException e) {
            log.error("Could not process response! method = " + httpMethod + "; url = " + url, e.getMessage());
            log.error(e.getResponseBodyAsString());
        }catch (RestClientException e) {
            log.error("Could not process response! method = " + httpMethod + "; url = " + url, e.getMessage());
        }

        return Optional.ofNullable(response);
    }

    private String createPayloadBase64(String payload) throws UnsupportedEncodingException {
        String payloadBase64 = Base64.getEncoder().encodeToString(payload.getBytes("UTF-8"));
        return payloadBase64;
    }

    private long getNonce() {

        return nonce++;
    }
}
