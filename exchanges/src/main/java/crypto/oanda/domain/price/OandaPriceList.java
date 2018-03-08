package crypto.oanda.domain.price;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OandaPriceList  {

    private List<OandaPrice> prices = new ArrayList<>();
}
