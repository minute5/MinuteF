package per.zongwlee.gitlab.infra.feign.fallback;

import io.choerodon.core.exception.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import per.zongwlee.agile.dto.IssueDTO;
import per.zongwlee.gitlab.infra.feign.AgileFeignClient;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/05
 */
@Component
public class AgileFeignClientFallback implements AgileFeignClient {

    @Override
    public ResponseEntity<IssueDTO> queryByBranchId(Long branchId) {
        throw new FeignException("error.query.agile.branch.id");
    }

    @Override
    public ResponseEntity updateBranch(Long issueId, Long branchId) {
        throw new FeignException("error.update.issue.branch.id");
    }

    @Override
    public ResponseEntity<Boolean> deleteByBranchId(Long branchId) {
        throw new FeignException("error.delete.agile.branch.id");
    }
}


