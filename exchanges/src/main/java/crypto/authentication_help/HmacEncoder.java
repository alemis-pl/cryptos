package crypto.authentication_help;

import crypto.persistance.apikey.ApiKeysDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.HashMap;

@Component
public class HmacEncoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(HmacEncoder.class);

    @Value("${algorithm.hmac}")
    private String algorithmHMACSHA384;

    public String hmacDigestBitfinex(Mac mac, String msg, String secretKey) {
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec(secretKey.getBytes("UTF-8"), algorithmHMACSHA384);
            mac = Mac.getInstance(algorithmHMACSHA384);
            mac.init(key);

            byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

            StringBuffer hash = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            digest = hash.toString();
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(HmacExceptionsMsg.ENCODING_ERROR.getException(), e);
        } catch (InvalidKeyException e) {
            LOGGER.error(HmacExceptionsMsg.INVALID_KEY_ERROR.getException(), e);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(HmacExceptionsMsg.LACK_OF_ALGORITHM_ERROR.getException(), e);
        }
        return digest;
    }

//    public String hmacDigistBitstamp(Mac mac, String msg, String secretKey) {
//        String digist = null;
//
//        try {
//            mac = Mac.getInstance(algorithmHMACSHA384);
//            SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), algorithmHMACSHA384);
//            mac.init(key);
//
//            byte[] hash = mac.doFinal(msg.getBytes());
//            StringBuilder sb = new StringBuilder();
//            for (byte a : hash) {
//                sb.append(String.format("%02x", a & 0xff));
//            }
//            digist = sb.toString().toUpperCase();
//            System.out.println(digist);
//
//        } catch (InvalidKeyException e) {
//            LOGGER.error(HmacExceptionsMsg.INVALID_KEY_ERROR.getException(), e);
//        } catch (NoSuchAlgorithmException e) {
//            LOGGER.error(HmacExceptionsMsg.LACK_OF_ALGORITHM_ERROR.getException(), e);
//        }
//        return digist;
//    }

    public String hmacDigistBitstamp2(Mac mac, Long nonce, ApiKeysDto apiKeys) {
        String signature = null;

        try {
            mac = Mac.getInstance(algorithmHMACSHA384);
            SecretKeySpec key = new SecretKeySpec(apiKeys.getApiSecretKey().getBytes(), algorithmHMACSHA384);
            mac.init(key);

            mac.update(String.valueOf(nonce).getBytes("UTF-8"));
            mac.update(apiKeys.getClientId().getBytes("UTF-8"));
            mac.update(apiKeys.getApiKey().getBytes("UTF-8"));

            byte[] hash = mac.doFinal(mac.doFinal());
            StringBuilder sb = new StringBuilder();
            for (byte a : hash) {
                sb.append(String.format("%02x", a & 0xff));
            }
            signature = sb.toString().toUpperCase();
        }catch (UnsupportedEncodingException e) {
                LOGGER.error(HmacExceptionsMsg.ENCODING_ERROR.getException(), e);
        } catch (InvalidKeyException e) {
            LOGGER.error(HmacExceptionsMsg.INVALID_KEY_ERROR.getException(), e);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(HmacExceptionsMsg.LACK_OF_ALGORITHM_ERROR.getException(), e);
        }
        return signature;
    }
}
