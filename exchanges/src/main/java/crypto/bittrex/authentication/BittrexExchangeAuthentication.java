package crypto.bittrex.authentication;

import crypto.authentication_help.HmacExceptionsMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Optional;

@Slf4j
@Component
public class BittrexExchangeAuthentication {

    @Value("${algorithm.hmacSHA512}")
    private String HmacSHA512;

    private RestTemplate restTemplate;

    public BittrexExchangeAuthentication (RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public HttpEntity createHttpEntity(String uri, String secretKey) {
        return new HttpEntity(createStandardHeaders(uri, secretKey));
    }

    private HttpHeaders createStandardHeaders(String uri, String secretKey) {
        String sign = calculateHMAC(uri, secretKey);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apisign", sign);
        return headers;
    }


    private String calculateHMAC(String url, String secretKey) {
        String sign = null;

        try {
            SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), HmacSHA512);
            Mac mac = Mac.getInstance(HmacSHA512);
            mac.init(key);
            sign = toHexString(mac.doFinal(url.getBytes()));
        } catch (InvalidKeyException e) {
            log.error(HmacExceptionsMsg.INVALID_KEY_ERROR.getException(), e);
        } catch (NoSuchAlgorithmException e) {
            log.error(HmacExceptionsMsg.LACK_OF_ALGORITHM_ERROR.getException(), e);
        }
        return sign;
    }

    private String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for(byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    public <T> Optional<T> getResponse(String url, HttpEntity<?> entity, Class<T> typeOfResponse, HttpMethod httpMethod) {
        T response = null;
        try {
            if (HttpMethod.POST.equals(httpMethod)) {
                restTemplate.getMessageConverters();
                response = restTemplate.postForObject(url, entity, typeOfResponse);
            }else {
                ResponseEntity<T> responseEntity = restTemplate.exchange(url, httpMethod, entity, typeOfResponse);
                if(responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.hasBody()) {
                    response = responseEntity.getBody();
                }
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
