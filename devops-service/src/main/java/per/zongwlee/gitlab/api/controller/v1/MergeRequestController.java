package per.zongwlee.gitlab.api.controller.v1;

import io.choerodon.core.exception.CommonException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.MergeRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import per.zongwlee.gitlab.app.service.MergeRequestService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1/{project_id}/merge_requests")
public class MergeRequestController {

    private static final String ERROR_MERGE_REQUEST_CREATE = "error.mergeRequest.create";
    private MergeRequestService mergeRequestService;

    public MergeRequestController(MergeRequestService mergeRequestService) {
        this.mergeRequestService = mergeRequestService;
    }

    /**
     * 创建merge请求
     *
     * @param projectId    工程ID
     * @param sourceBranch 源分支
     * @param targetBranch 目标分支
     * @param title        标题
     * @param description  描述
     * @param userId       用户Id
     * @return MergeRequest
     */
    @ApiOperation(value = "创建merge请求")
    @PostMapping
    public ResponseEntity<MergeRequest> create(
            @ApiParam(value = "工程id", required = true)
            @PathVariable(value = "project_id") Integer projectId,
            @ApiParam(value = "要创建的分支名", required = true)
            @RequestParam("source_branch") String sourceBranch,
            @ApiParam(value = "源分支名", required = true)
            @RequestParam("target_branch") String targetBranch,
            @ApiParam(value = "源分支名", required = true)
            @RequestParam("title") String title,
            @ApiParam(value = "源分支名", required = true)
            @RequestParam("description") String description,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "user_id", required = false) Integer userId) {
        return Optional.ofNullable(mergeRequestService.createMergeRequest(projectId,
                sourceBranch, targetBranch, title, description, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException(ERROR_MERGE_REQUEST_CREATE));
    }


    /**
     * 获取合并请求merge request
     *
     * @param projectId      项目id
     * @param mergeRequestId 合并请求id
     * @param userId         用户Id
     * @return MergeRequest
     */
    @ApiOperation(value = "获取合并请求merge request")
    @GetMapping(value = "/{merge_request_id}")
    public ResponseEntity<MergeRequest> query(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "project_id") Integer projectId,
            @ApiParam(value = "合并请求id", required = true)
            @PathVariable("merge_request_id") Integer mergeRequestId,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "user_id", required = false) Integer userId) {
        return Optional.ofNullable(mergeRequestService.queryMergeRequest(projectId, mergeRequestId, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException(ERROR_MERGE_REQUEST_CREATE));
    }

    /**
     * 获取合并请求列表merge request
     *
     * @param projectId 项目id
     * @return MergeRequest
     */
    @ApiOperation(value = "获取合并请求列表merge request")
    @GetMapping
    public ResponseEntity<List<MergeRequest>> list(
            @ApiParam(value = "工程id", required = true)
            @PathVariable(value = "project_id") Integer projectId) {
        return Optional.ofNullable(mergeRequestService.listMergeRequests(projectId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException(ERROR_MERGE_REQUEST_CREATE));
    }

    /**
     * 执行merge请求
     *
     * @param projectId                项目id
     * @param mergeRequestId           merge请求的id
     * @param mergeCommitMessage       merge的commit信息
     * @param shouldRemoveSourceBranch merge后是否删除该分支
     * @param userId                   用户Id
     * @return MergeRequest
     */
    @ApiOperation(value = "执行merge请求")
    @PutMapping(value = "/{merge_request_id}/merge")
    public ResponseEntity<MergeRequest> acceptMergeRequest(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "project_id") Integer projectId,
            @ApiParam(value = "merge请求的id", required = true)
            @PathVariable(value = "merge_request_id") Integer mergeRequestId,
            @ApiParam(value = "merge的commit信息", required = true)
            @RequestParam("merge_commit_message") String mergeCommitMessage,
            @ApiParam(value = "merge后是否删除该分支")
            @RequestParam("remove_source_branch") Boolean shouldRemoveSourceBranch,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "user_id", required = false) Integer userId) {
        return Optional.ofNullable(mergeRequestService.acceptMergeRequest(projectId,
                mergeRequestId, mergeCommitMessage, shouldRemoveSourceBranch, true, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.mergeRequest.accept"));
    }

    /**
     * 查询合并请求的commits
     *
     * @param projectId      项目id
     * @param mergeRequestId 合并请求ID
     * @param userId         用户Id
     * @return List
     */
    @ApiOperation(value = "查询合并请求的commits")
    @GetMapping(value = "/{merge_request_id}/commit")
    public ResponseEntity<List<Commit>> listCommits(
            @ApiParam(value = "项目ID", required = true)
            @PathVariable(value = "project_id") Integer projectId,
            @ApiParam(value = "合并请求ID", required = true)
            @PathVariable("merge_request_id") Integer mergeRequestId,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "user_id") Integer userId) {
        return Optional.ofNullable(mergeRequestService.listCommits(projectId, mergeRequestId, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.mergeRequest.merge"));
    }

    /**
     * 删除合并请求
     *
     * @param projectId      项目id
     * @param mergeRequestId 合并请求ID
     * @return List
     */
    @ApiOperation(value = "删除合并请求")
    @DeleteMapping(value = "/{merge_request_id}")
    public ResponseEntity delete(@PathVariable(value = "project_id") Integer projectId,
                                 @PathVariable(value = "merge_request_id") Integer mergeRequestId) {
        mergeRequestService.deleteMergeRequest(projectId, mergeRequestId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
