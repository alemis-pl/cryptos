package crypto.strategy.domain.simplestrategy;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SimpleStrategyResponse {

    private SimpleStrategyDto simpleStrategyDto;
    private boolean saved;
}
