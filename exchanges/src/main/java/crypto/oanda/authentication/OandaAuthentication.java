package crypto.oanda.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Component
public class OandaAuthentication {

    private static final String STANDARD_REQUEST = "STANDARD_REQUEST";
    private static final String ORDER_REQUEST = "ORDER_REQUEST";

    private RestTemplate restTemplate;

    @Autowired
    public OandaAuthentication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public HttpEntity createHeaders(String token, String accountId, String requestType) {
        HttpEntity entity = null;

        switch (requestType) {
            case STANDARD_REQUEST:
                entity = new HttpEntity(createStandardHeaders(token));
                 log.info("Entity for standard request created!");
                break;
            case ORDER_REQUEST:
                entity = new HttpEntity(createOrderHeaders(token));
                log.info("Entity for order request created!");
                break;
            default:
                log.info("No entity created! Incorrect request type [" + requestType + "]");
        }
        return entity;
    }

    private HttpHeaders createStandardHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }

    private HttpHeaders createOrderHeaders(String token) {
        HttpHeaders headers = createStandardHeaders(token);
        //implementation needed!

        return headers;
    }

    public <T> Optional<T> getResponse(String url, HttpEntity<?> entity, Class<T> typeOfResponse, HttpMethod httpMethod) {
        T response = null;

        try {
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
}
