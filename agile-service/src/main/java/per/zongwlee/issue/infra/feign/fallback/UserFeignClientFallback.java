package per.zongwlee.issue.infra.feign.fallback;

import io.choerodon.core.exception.CommonException;
import io.choerodon.core.exception.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import per.zongwlee.issue.infra.feign.UserFeignClient;
import per.zongwlee.oauth.api.dto.RoleDTO;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/05
 */
@Component
public class UserFeignClientFallback implements UserFeignClient {


    @Override
    public ResponseEntity<RoleDTO> queryById(Long roleId) {
        throw new FeignException("error.query.user.with.id");
    }
}


