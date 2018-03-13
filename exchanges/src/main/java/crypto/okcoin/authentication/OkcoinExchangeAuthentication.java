package crypto.okcoin.authentication;

import crypto.okcoin.domain.params.OkcoinParamsModerator;
import crypto.persistance.apikey.ApiKeys;
import crypto.persistance.apikey.ApiKeysDto;
import crypto.persistance.mapper.ApiKeysMapper;
import crypto.persistance.service.DbService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class OkcoinExchangeAuthentication {

    @Value("${okex.main.url}")
    private String mainUrl;

    @Autowired
    private DbService dbService;

    @Autowired
    private ApiKeysMapper apiKeysMapper;

    @Autowired
    private OkcoinRequestParamsModifier paramsModifier;

    private HttpClient httpClient;

    private static long startTime = System.currentTimeMillis();

    private static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

    //Only for tests
    private ApiKeysDto getApiKey() {
        ApiKeys apiKeys = dbService.getApiKeysByExchange("okex");
        return apiKeysMapper.mapApiKeysToApiKeysDto(apiKeys);
    }


    private static ConnectionKeepAliveStrategy keepAliveStrategy = new DefaultConnectionKeepAliveStrategy() {
        public long getKeepAliveDuration(
                HttpResponse response,
                HttpContext context) {
            long keepAlive = super.getKeepAliveDuration(response, context);

            if (keepAlive == -1) {
                keepAlive = 5000;
            }
            return keepAlive;
        }
    };

    private OkcoinExchangeAuthentication() {
        httpClient = HttpClients.custom().setConnectionManager(cm).setKeepAliveStrategy(keepAliveStrategy).build();
    }

    private void IdleConnectionMonitor(){

        if(System.currentTimeMillis()-startTime>30000){
            startTime = System.currentTimeMillis();
            cm.closeExpiredConnections();
            cm.closeIdleConnections(30, TimeUnit.SECONDS);
        }
    }

    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(20000)
            .setConnectTimeout(20000)
            .setConnectionRequestTimeout(20000)
            .build();

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public HttpPost httpPostMethod(String url) {
        return new HttpPost(url);
    }

    private HttpRequestBase httpGetMethod(String url) {
        return new HttpGet(url);
    }

    public String requestHttpPost(String endpoint, OkcoinParamsModerator paramsModerator) throws HttpException, IOException {
        Map<String, String> params = new HashMap<>();
        ApiKeysDto apiKeysDto = getApiKey();
        params.put("api_key", apiKeysDto.getApiKey());
        params.putAll(paramsModifier.modifyRequestParamMap(paramsModerator));
        String sign = buildMysign(params, apiKeysDto.getApiSecretKey());
        params.put("sign", sign);

        IdleConnectionMonitor();
        String finalUrl = mainUrl + endpoint;
        HttpPost method = httpPostMethod(finalUrl);
        List<NameValuePair> valuePairs = convertMap2PostParams(params);
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
        method.setEntity(urlEncodedFormEntity);
        method.setConfig(requestConfig);
        HttpResponse response = httpClient.execute(method);
        HttpEntity entity =  response.getEntity();
        if(entity == null){
            return "";
        }
        InputStream is = null;
        String responseData = "";
        try{
            is = entity.getContent();
            responseData = IOUtils.toString(is, "UTF-8");
        }finally{
            if(is!=null){
                is.close();
            }
        }
        return responseData;

    }

    private List<NameValuePair> convertMap2PostParams(Map<String,String> params){
        List<String> keys = new ArrayList<String>(params.keySet());
        if(keys.isEmpty()){
            return null;
        }
        int keySize = keys.size();
        List<NameValuePair>  data = new LinkedList<NameValuePair>() ;
        for(int i=0;i<keySize;i++){
            String key = keys.get(i);
            String value = params.get(key);
            data.add(new BasicNameValuePair(key,value));
        }
        return data;
    }


    public String buildMysign(Map<String, String> params, String secretKey) {
        String mysign = "";
        try {
            String prestr = createLinkString(params);
            prestr = prestr + "&secret_key=" + secretKey;
            mysign = getMD5String(prestr);
        }catch (Exception e ) {
            e.printStackTrace();
        }
        return mysign;
    }

    private String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    private String getMD5String(String str) {
        try {
            if (str == null || str.trim().length() == 0) {
                return "";
            }
            byte[] bytes = str.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);
            bytes = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(HEX_DIGITS[(bytes[i] & 0xf0) >> 4] + ""
                        + HEX_DIGITS[bytes[i] & 0xf]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}

