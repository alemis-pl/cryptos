package crypto.okex.domain.accountbalance;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OkexInfoDto {

    private OkexFundsDto funds;
}
