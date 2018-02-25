package crypto.bitstamp.authentication;

import com.google.gson.Gson;
import crypto.authentication_help.ContentGenerator;
import crypto.authentication_help.ExchangeHttpResponse;
import crypto.authentication_help.HmacEncoder;
import crypto.persistance.apikey.ApiKeys;
import crypto.persistance.apikey.ApiKeysDto;
import crypto.persistance.mapper.ApiKeysMapper;
import crypto.persistance.service.DbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

@Component
public class BitstampExchangeAuthentication {

    @Value("bitstamp.main.url")
    private String bitstampMainUrl;

    @Value("${algorithm.hmac}")
    private String algorithmHMACSHA384;

    private static final Logger LOGGER = LoggerFactory.getLogger(BitstampExchangeAuthentication.class);

    private long nonce = System.currentTimeMillis();
    private int connectionTimeout;

    private Gson gson = new Gson();
    private Mac mac;

    @Autowired
    private DbService dbService;

    @Autowired
    private HmacEncoder hmacEncoder;

    @Autowired
    private ApiKeysMapper apiKeysMapper;

    @Autowired
    private ContentGenerator contentGenerator;

    //Actually, api operates on 1 api key for 1 user.
    //Further development of the application will create
    // necessity for developing of functionalities for many users.
    private ApiKeysDto getUserApiKeys() {
        ApiKeys apiKeys = dbService.getApiKeysByExchange("bitstamp");
        return apiKeysMapper.mapApiKeysToApiKeysDto(apiKeys);
    }

    public ExchangeHttpResponse sendRequest(String urlPath, String httpMethod) {
        ApiKeysDto apiKeysDto = getUserApiKeys();
        String userId = null; // change needed!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        HttpURLConnection connection = null;
        Long nonce = getNonce();

        try {
            URL url = new URL(bitstampMainUrl + urlPath);
            LOGGER.debug("Using following URL for API call: " + url);

            String parameters = provideParamteres(userId, apiKeysDto, nonce);

            connection = setHttpUrlConnParameters(url, httpMethod, parameters);
            String content = contentGenerator.createContent(connection);

            return new ExchangeHttpResponse(connection.getResponseCode(), connection.getResponseMessage(), content);

        } catch (MalformedURLException e) {
            LOGGER.error(BitstampExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
        } catch(SocketTimeoutException e) {
            LOGGER.error(BitstampExchangeConnectionExceptions.IO_SOCKET_TIMEOUT_ERROR_MSG.getException(), e);
        } catch(FileNotFoundException | UnknownHostException e) {
            LOGGER.error(BitstampExchangeConnectionExceptions.CONNECTION_ERROR.getException(), e);
        }catch (IOException io) {
            LOGGER.error(BitstampExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), io);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return new ExchangeHttpResponse(0, null, null);
    }
    
    private long getNonce() {
        return ++nonce;
    }

    private HttpURLConnection setHttpUrlConnParameters(URL url, String httpMethod, String parameters) throws IOException {
        byte[] postData = parameters.getBytes(StandardCharsets.UTF_8);

        HttpURLConnection connection = null;
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(httpMethod);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.addRequestProperty("charset", "UTF-8");
        connection.addRequestProperty("Content-Length", Integer.toString(postData.length));
        connection.setUseCaches(false);
        return connection;
    }

    private String provideParamteres(String userId, ApiKeysDto apiKeys, Long nonce) {
        String parameters = "key=" + apiKeys.getApiKey() + "%signature=" + createSignature(userId, apiKeys.getApiSecretKey(), nonce) + "&nonce=" + nonce;
        return parameters;
    }

    private String createSignature(String userId, String secretKey, Long nonce) {
        String message = nonce + userId + secretKey;
        String signature = hmacEncoder.hmacDigest(mac, message, secretKey);
        return signature;
    }
}
