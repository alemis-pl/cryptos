package crypto.strategy.controller;

import crypto.strategy.domain.simplestrategy.SimpleStrategyResponse;
import crypto.strategy.domain.simplestrategy.SimpleStrategyDto;
import crypto.strategy.facade.SimpleStrategyFacade;
import crypto.strategy.mapper.SimpleStrategyMapper;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v1/crypto/strategy")
public class IndividualStrategyController {

    private SimpleStrategyFacade individualStrategyFacade;
    private SimpleStrategyMapper individualStrategyInfoMapper;

    public IndividualStrategyController(SimpleStrategyFacade individualStrategyFacade, SimpleStrategyMapper individualStrategyInfoMapper) {
        this.individualStrategyFacade = individualStrategyFacade;
        this.individualStrategyInfoMapper = individualStrategyInfoMapper;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add_strategy")
    public SimpleStrategyResponse addIndividualStrategy(@RequestBody SimpleStrategyDto individualStrategyInfoDto) {
        return individualStrategyFacade.addIndividualStrategy(individualStrategyInfoMapper.convertToSimpleStrategy(individualStrategyInfoDto));
    }


}
