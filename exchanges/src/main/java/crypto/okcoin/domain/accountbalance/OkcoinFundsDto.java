package crypto.okcoin.domain.accountbalance;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OkcoinFundsDto {

    private OkcoinBorrowFundsDto borrow;
    private OkcoinFreeFundsDto free;
    private OkcoinFreezedFundsDto freezed;
}
