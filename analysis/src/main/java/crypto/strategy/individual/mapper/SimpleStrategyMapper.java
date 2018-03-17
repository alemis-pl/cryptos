package crypto.strategy.individual.mapper;

import crypto.strategy.individual.domain.signal.SimpleStrategy;
import crypto.strategy.individual.domain.signal.SimpleStrategyDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleStrategyMapper {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public SimpleStrategy convertToSimpleStrategy(SimpleStrategyDto simpleStrategyDto) {
        return modelMapper().map(simpleStrategyDto, SimpleStrategy.class);
    }

    public SimpleStrategyDto convertToSimpleStrategyDto(SimpleStrategy simpleStrategy) {
        return modelMapper().map(simpleStrategy, SimpleStrategyDto.class);
    }
}
