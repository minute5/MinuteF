package per.zongwlee.issue.infra.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/14
 */

@Component
@Scope("singleton")
public class AgileModelMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    private AgileModelMapper() {
    }

    public <T> T convert(Object source, Class<T> destinationClass) {
        if (modelMapper.getConfiguration().getMatchingStrategy() != MatchingStrategies.STRICT)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(source, destinationClass);
    }
}
