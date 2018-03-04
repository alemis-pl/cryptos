package forex.oanda.authentication;




import forex.oanda.domain.instrument.OandaInstrument;
import org.apache.http.client.methods.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;



import java.util.List;
import java.util.Optional;

@Component
public class OandaAuthentication {

    @Value("${oanda.main.url}")
    private String oandaMainUrl;

    public String createUrl(String pathUrlType, String accountId, List<OandaInstrument> instruments) {
        StringBuilder url = new StringBuilder();
        url.append(oandaMainUrl);
        url.append(accountId);
        url.append("/");
        url.append(pathUrlType);

        instruments.forEach(intrument -> {
            int count = 0;
            url.append(instruments);
            if(count != instruments.size()-1) {
                url.append("%2C");
            }
            count++;
            System.out.println(count);
        });

        System.out.println(url.toString());
        return url.toString();
    }

    public HttpEntity createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization: ", "Bearer " + token);
        headers.set("X-Accept-Datetime-Format", "UNIX");
        return new HttpEntity(headers);
    }
}
