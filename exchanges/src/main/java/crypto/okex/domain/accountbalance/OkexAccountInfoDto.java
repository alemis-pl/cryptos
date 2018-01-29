package crypto.okex.domain.accountbalance;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OkexAccountInfoDto {

    private boolean result;
    private OkexInfoDto info;
}
