package crypto.strategy.domain.simplestrategy;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import javax.persistence.*;
import java.math.BigDecimal;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "simple_strategy")
public class SimpleStrategy {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "instrument_type")
    private String instrumentType;

    @Column(name = "instrument")
    private String instrument;

    @Column(name = "position")
    private String position;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "price_achieved")
    private boolean achieved;

    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "exchange")
    private String exchange;

    @Column(name = "market_price_is")
    private String marketPriceIs;

}
