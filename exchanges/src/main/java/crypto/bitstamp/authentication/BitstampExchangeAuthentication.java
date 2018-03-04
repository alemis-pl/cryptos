package crypto.bitstamp.authentication;

import com.google.gson.Gson;
import crypto.authentication_help.ContentGenerator;
import crypto.authentication_help.ExchangeHttpResponse;
import crypto.authentication_help.HmacEncoder;
import crypto.authentication_help.HmacExceptionsMsg;
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
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Component
public class BitstampExchangeAuthentication {

    @Value("${bitstamp.main.url}")
    private String bitstampMainUrl;

    @Value("${algorithm.hmac}")
    private String algorithmHMACSHA384;

    private static final Logger LOGGER = LoggerFactory.getLogger(BitstampExchangeAuthentication.class);

    private long nonce = System.currentTimeMillis();
    private int connectionTimeout;

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

        HttpURLConnection connection = null;
        Long nonce = getNonce();

        try {
            final StringBuilder postData = new StringBuilder("");
            HashMap<String, String> params = createParams(apiKeysDto, nonce);

            params.entrySet().forEach(param ->  {
                if (postData.length() > 0) {
                    postData.append("&");
                }
                postData.append(param.getKey());
                postData.append("=");
                try {
                    postData.append(URLEncoder.encode(param.getValue(), "UTF-8"));
                }catch (UnsupportedEncodingException e) {
                    LOGGER.error(HmacExceptionsMsg.ENCODING_ERROR.getException(), e);
                }
            });

            URL url = new URL(bitstampMainUrl + urlPath);

            LOGGER.debug("Using following URL for API call: " + url);

            connection = setHttpUrlConnParameters(url, httpMethod, postData.toString());
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

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod(httpMethod);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.addRequestProperty("charset", "utf-8");
        connection.addRequestProperty("Content-Length", Integer.toString(postData.length));
        connection.setUseCaches(false);
        return connection;
    }

    private String createSignature(Long nonce, ApiKeysDto apiKeys) {
        String signature = hmacEncoder.hmacDigistBitstamp2(mac, nonce, apiKeys);
        return signature;
    }

    private HashMap<String, String> createParams(ApiKeysDto apiKeys, Long nonce) {
        HashMap<String, String> params = new HashMap<>();
        params.put("key", apiKeys.getApiKey());
        params.put("nonce", Long.toString(nonce));
        params.put("signature", createSignature(nonce, apiKeys));
        return params;
    }
}
