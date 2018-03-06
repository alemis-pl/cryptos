package crypto.oanda.authentication;




import crypto.oanda.domain.instrument.OandaInstrument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;
import java.util.List;

@Component
public class OandaAuthentication {



    public HttpEntity createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        return new HttpEntity(headers);
    }
}
