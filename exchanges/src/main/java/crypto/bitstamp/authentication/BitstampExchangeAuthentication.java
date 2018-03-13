package crypto.bitstamp.authentication;

import crypto.persistance.apikey.ApiKeys;
import crypto.persistance.service.DbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Slf4j
@Component
public class BitstampExchangeAuthentication {


    private DbService dbService;
    private RestTemplate restTemplate;

    public BitstampExchangeAuthentication(DbService dbService, RestTemplate restTemplate) {
        this.dbService = dbService;
        this.restTemplate = restTemplate;
    }

    private ApiKeys getUserApiKeys() {
        return dbService.getApiKeysByExchange("bitstamp");
    }

    public HttpEntity<MultiValueMap<String, String>> createHttpEntity() {
        HttpHeaders headers = createHeaders();
        MultiValueMap<String, String> params = createParams();
        return new HttpEntity<MultiValueMap<String, String>>(params, headers);
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    private MultiValueMap<String, String> createParams() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        Long nonce = System.currentTimeMillis();
        String key = getUserApiKeys().getApiKey();
        String signature = getSignature(nonce);
        params.add("key", key);
        params.add("signature", signature);
        params.add("nonce", nonce.toString());
        return params;
    }

    public <T> Optional<T> getResponse(String url, HttpEntity<?> entity, Class<T> typeOfResponse, HttpMethod httpMethod) {
        T response = null;

        try {
            if (HttpMethod.POST.equals(httpMethod)) {
//                restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
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

    private String getSignature(long nonce) {
        String id = getUserApiKeys().getClientId();
        String key = getUserApiKeys().getApiKey();
        String secret = getUserApiKeys().getApiSecretKey();

        String message=nonce+id+key;


        String signature="";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] hash = sha256_HMAC.doFinal(message.getBytes());
            signature = byteArrayToHex(hash).toUpperCase();


        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return signature;

    }

    private String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }


}
