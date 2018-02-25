package crypto.authentication_help;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class HmacEncoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(HmacEncoder.class);

    @Value("${algorithm.hmac}")
    private String algorithmHMACSHA384;

    public String hmacDigest(Mac mac, String msg, String keyString) {
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), algorithmHMACSHA384);
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
}
