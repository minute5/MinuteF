package per.zongwlee.gitlab.infra.feign;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import per.zongwlee.agile.dto.IssueDTO;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/05
 */
@FeignClient(value = "agile-service",
        fallback = AgileFeignClient.class)
@Component
public interface AgileFeignClient {

    @GetMapping("/v1/issue/{branch_id}")
    ResponseEntity<IssueDTO> queryByBranchId(@ApiParam(value = "branch_id", required = true)
                                                    @PathVariable("branch_id") Long branchId);

    @DeleteMapping(value = "/v1/issue/{branch_id}")
    ResponseEntity<Boolean> deleteByBranchId(@PathVariable("branch_id") Long branchId);
}
