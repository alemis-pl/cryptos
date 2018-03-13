package crypto.okcoin.domain.accountbalance;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OkcoinAccountInfoDto {

    private boolean result;
    private OkcoinInfoDto info;
}
