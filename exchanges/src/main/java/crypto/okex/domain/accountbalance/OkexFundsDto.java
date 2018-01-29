package crypto.okex.domain.accountbalance;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OkexFundsDto {

    private OkexBorrowFundsDto borrow;
    private OkexFreeFundsDto free;
    private OkexFreezedFundsDto freezed;
}
