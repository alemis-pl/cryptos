package crypto.bitfinex.domain.apikey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name="api_keys")
public class ApiKeys {

    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;

    @Column(name="api_key")
    private String apiKey;

    @Column(name="api_secret_key")
    private String apiSecretKey;
}
