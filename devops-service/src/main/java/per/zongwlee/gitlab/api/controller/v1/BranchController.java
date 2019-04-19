package per.zongwlee.gitlab.api.controller.v1;

import io.choerodon.core.exception.CommonException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import per.zongwlee.gitlab.app.service.RepositoryService;
import per.zongwlee.gitlab.domain.entity.Branch;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1/{project_id}/branch")
public class BranchController {

    private RepositoryService repositoryService;

    public BranchController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    /**
     * 创建新分支
     *
     * @param projectId 项目id
     * @param name      分支名
     * @param source    源分支名
     * @param userId    用户Id
     * @return Branch
     */
    @ApiOperation(value = "创建新分支")
    @PostMapping
    public ResponseEntity<Branch> createBranch(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "project_id") Integer projectId,
            @ApiParam(value = "分支名", required = true)
            @RequestParam("name") String name,
            @ApiParam(value = "源分支名", required = true)
            @RequestParam("source") String source,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "user_id") Integer userId,
            @ApiParam(value = "关联issueId")
            @RequestParam(value = "issue_id") Long issueId
    ) {
        return Optional.ofNullable(repositoryService.createBranch(projectId, name, source, userId, issueId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.branch.create"));
    }

    /**
     * 根据分支名删除分支
     *
     * @param projectId  项目id
     * @param branchName 分支名
     * @param userId     用户Id
     */
    @ApiOperation(value = "根据分支名删除分支")
    @DeleteMapping(value = "/{branch_name}")
    public ResponseEntity deleteBranch(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "project_id") Integer projectId,
            @ApiParam(value = "要删除的分支名", required = true)
            @PathVariable("branch_name") String branchName,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "user_id", required = false) Integer userId) {
        repositoryService.deleteBranch(projectId, branchName, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 根据分支名查询分支
     *
     * @param projectId  项目id
     * @param branchName 分支名
     * @return Branch
     */
    @ApiOperation(value = "根据分支名查询分支")
    @GetMapping(value = "/{branch_name}")
    public ResponseEntity<Branch> queryBranchByName(
            @ApiParam(value = "工程id", required = true)
            @PathVariable(value = "project_id") Integer projectId,
            @ApiParam(value = "要查询的分支名", required = true)
            @PathVariable("branch_name") String branchName) {
        return Optional.ofNullable(repositoryService.queryBranchByName(projectId, branchName))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.branch.query"));
    }

    /**
     * 获取项目下所有分支
     *
     * @param projectId 项目id
     * @param userId    用户Id
     * @return List
     */
    @ApiOperation(value = "获取工程下所有分支")
    @GetMapping(value = "/all")
    public ResponseEntity<List<Branch>> listBranches(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "project_id") Integer projectId,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "user_id", required = false) Integer userId) {
        return Optional.ofNullable(repositoryService.listBranches(projectId, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.branch.list"));
    }
}
