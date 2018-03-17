package crypto.strategy.individual.domain.signal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "individual_strategy")
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }

    public void setTimestamp(Long timestamp) {
        if(this.timestamp == null) {
            this.timestamp = System.currentTimeMillis();
        }else {
            this.timestamp = timestamp;
        }
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
}
