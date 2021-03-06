package per.zongwlee.test.manager.infra.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import per.zongwlee.oauth.api.dto.RoleDTO;
import per.zongwlee.test.manager.infra.feign.fallback.UserFeignClientFallback;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/05
 */
@FeignClient(value = "oauth-server",path = "/oauth",
        fallback = UserFeignClientFallback.class)
@Component
public interface UserFeignClient {

    @GetMapping(value = "/{role_id}")
    ResponseEntity<RoleDTO> queryById(@PathVariable("role_id") Long roleId);
}
