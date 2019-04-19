package per.zongwlee.oauth.infra.feign.fallback;

import io.choerodon.core.exception.FeignException;
import org.gitlab4j.api.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import per.zongwlee.oauth.infra.feign.DevopsFeignClient;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/16
 */
@Component
public class DevopsFeignClientFallback implements DevopsFeignClient {

    @Override
    public ResponseEntity<User> create(String password, Integer projectsLimit, User user) {
        throw new FeignException("error.gitlab.create.user");
    }

    @Override
    public ResponseEntity<Integer> queryUserByUsername(String username) {
        throw new FeignException("error.gitlab.get.user.by.name");
    }
}


