package crypto.persistance.apikey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@ToString
public class ApiKeysDto {

    private String apiKey;
    private String apiSecretKey;
    private String clientId;
}
