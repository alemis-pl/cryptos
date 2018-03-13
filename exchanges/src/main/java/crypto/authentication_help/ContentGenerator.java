package crypto.authentication_help;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;


@Component
public class ContentGenerator {

    public String createContent(HttpURLConnection connection) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.out.println(baos);
        byte[] buf = new byte[1024];
        int n = 0;
        while((n = connection.getInputStream().read(buf)) >= 0) {
            baos.write(buf, 0, n);
        }
        byte[] content = baos.toByteArray();
        return new String(content);
    }
}
