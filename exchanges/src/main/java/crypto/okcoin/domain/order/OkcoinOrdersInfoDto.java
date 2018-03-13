package crypto.okcoin.domain.order;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OkcoinOrdersInfoDto {

    private boolean result;
    private OkcoinOrderInfoListDto orders;

}
