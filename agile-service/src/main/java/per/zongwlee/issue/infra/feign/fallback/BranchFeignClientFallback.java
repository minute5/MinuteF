package per.zongwlee.issue.infra.feign.fallback;

import io.choerodon.core.exception.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import per.zongwlee.devops.Branch;
import per.zongwlee.issue.infra.feign.BranchFeignClient;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/05
 */
@Component
public class BranchFeignClientFallback implements BranchFeignClient {


    @Override
    public ResponseEntity<Branch> queryBranchById(Long projectId, Long branchId) {
        throw new FeignException("error.query.branch.id");
    }
}


