package per.zongwlee.gateway.feign.fallback;

import io.choerodon.core.exception.FeignException;
import org.springframework.stereotype.Component;
import per.zongwlee.gateway.feign.UserFeignClient;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/05
 */
@Component
public class UserFeignClientFallback implements UserFeignClient {


    @Override
    public Boolean checkAuthorazition(String jwtToken) {
        throw new FeignException("error.check.token");
    }
}


