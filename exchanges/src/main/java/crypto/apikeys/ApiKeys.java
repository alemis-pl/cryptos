package crypto.apikeys;

import lombok.Data;

import javax.persistence.*;

@NamedQuery(
        name = "ApiKeys.getByExchange",
        query = "FROM ApiKeys WHERE exchange = :EXCHANGE"
)

@Data
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

    @Column(name="client_id")
    private String clientId;

    @Column(name="exchange")
    private String exchange;
}
