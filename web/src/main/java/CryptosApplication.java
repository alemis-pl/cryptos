import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"crypto"})
@EnableJpaRepositories(basePackages = {"crypto"})
@EntityScan(basePackages = {"crypto"})
@ComponentScan(basePackages = ("crypto"))
public class CryptosApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptosApplication.class);
    }
}
