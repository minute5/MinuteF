package per.zongwlee.gateway.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import per.zongwlee.gateway.feign.fallback.UserFeignClientFallback;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/05
 */
@FeignClient(value = "oauth-server",path = "/oauth",
        fallback = UserFeignClientFallback.class)
@Component
public interface UserFeignClient {

    @GetMapping(value = "/v1/check/authorazition")
    Boolean checkAuthorazition(@RequestParam("jwtToken") String jwtToken);
}
