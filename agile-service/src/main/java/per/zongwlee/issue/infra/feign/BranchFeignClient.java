package per.zongwlee.issue.infra.feign;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import per.zongwlee.devops.Branch;
import per.zongwlee.issue.infra.feign.fallback.BranchFeignClientFallback;
import per.zongwlee.issue.infra.feign.fallback.UserFeignClientFallback;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/05
 */
@FeignClient(value = "devops-gitlab-service",
        fallback = BranchFeignClientFallback.class)
@Component
public interface BranchFeignClient {

    /**
     * 根据分支id查询分支
     *
     * @param projectId 项目id
     * @param branchId  分支id
     * @return Branch
     */
    @ApiOperation(value = "根据分支id查询分支")
    @GetMapping(value = "/v1/{project_id}/branch/id/{branch_id}")
    ResponseEntity<Branch> queryBranchById(
            @PathVariable("project_id") Long projectId,
            @PathVariable("branch_id") Long branchId);
}
