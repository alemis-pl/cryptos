package crypto.okex.domain.order;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OkexOrdersInfoDto {

    private boolean result;
    private OkexOrderInfoListDto orders;

}
