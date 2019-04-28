package per.zongwlee.issue.api.controller;

import io.choerodon.core.base.BaseController;
import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import per.zongwlee.issue.api.dto.IssueDTO;
import per.zongwlee.issue.domain.entity.Issue;
import per.zongwlee.issue.domain.service.IssueService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/12
 */
@RestController
@RequestMapping(value = "/v1/issue")
public class IssueController extends BaseController {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private IssueService issueService;

    private static final Long TYPE = 0L;

    @ApiOperation(value = "创建问题")
    @PostMapping
    public ResponseEntity<IssueDTO> create(@RequestBody IssueDTO issueDTO) {
        return new ResponseEntity<>(issueService.create(issueDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "删除问题")
    @DeleteMapping(value = "/{issue_id}")
    public ResponseEntity<Boolean> delete(@PathVariable("issue_id") Long issueId) {
        return new ResponseEntity<>(issueService.deleteByPrimaryKey(issueId) == 1, HttpStatus.OK);
    }

    @ApiOperation(value = "根据BranchId删除问题")
    @DeleteMapping(value = "/branch/{branch_id}")
    public ResponseEntity<Boolean> deleteByBranchId(@PathVariable("branch_id") Long branchId) {
        Issue issue = new Issue();
        issue.setBranchId(branchId);
        return new ResponseEntity<>(issueService.delete(issue) == 1, HttpStatus.OK);
    }

    @ApiOperation(value = "更新问题")
    @PutMapping
    public ResponseEntity<IssueDTO> update(@RequestBody @Valid IssueDTO issueDTO) {
        return new ResponseEntity<>(issueService.updateIssue(issueDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "查询所有问题")
    @GetMapping("/list/all")
    public ResponseEntity<Page<IssueDTO>> queryAll(
            @ApiParam(value = "分页参数") PageRequest pageRequest) {
        return Optional.ofNullable(issueService.pageQuery(pageRequest,TYPE))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.issue.list.get"));
    }

    @ApiOperation(value = "查询待办问题")
    @GetMapping("/list/backlog")
    public ResponseEntity<Page<IssueDTO>> queryBacklogs(
            @ApiParam(value = "分页参数") PageRequest pageRequest) {
        return Optional.ofNullable(issueService.pageQueryBacklog(pageRequest,TYPE))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.issue.backlog.get"));
    }

    @ApiOperation(value = "查询活跃问题")
    @GetMapping("/list/active")
    public ResponseEntity<Page<IssueDTO>> queryActiveMatters(
            @ApiParam(value = "分页参数") PageRequest pageRequest) {
        return Optional.ofNullable(issueService.pageQueryActiveMatters(pageRequest,TYPE))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.issue.active.get"));
    }

    @ApiOperation(value = "查询完成问题")
    @GetMapping("/list/finished")
    public ResponseEntity<Page<IssueDTO>> queryFinishedMatters(
            @ApiParam(value = "分页参数") PageRequest pageRequest) {
        return Optional.ofNullable(issueService.pageQueryFinishedMatters(pageRequest,TYPE))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.issue.finished.get"));
    }

    @ApiOperation(value = "不分页查询问题")
    @GetMapping("/list")
    public ResponseEntity<List<IssueDTO>> queryAllMatters() {
        return Optional.ofNullable(issueService.queryMatters())
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.issue.finished.get"));
    }

    @ApiOperation(value = "根据id查询问题")
    @GetMapping("/{issue_id}")
    public ResponseEntity<IssueDTO> queryById(@ApiParam(value = "issue_id", required = true)
                                              @PathVariable("issue_id") Long issueId) {
        return Optional.ofNullable(modelMapper.map(issueService.queryById(issueId), IssueDTO.class))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.issue.id.get"));

    }

    @ApiOperation(value = "根据branch_id查询问题")
    @GetMapping("/branch/{branch_id}")
    public ResponseEntity<IssueDTO> queryByBranchId(@ApiParam(value = "branch_id", required = true)
                                              @PathVariable("branch_id") Long branchId) {
        return Optional.ofNullable(issueService.queryByBranchId(branchId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.issue.id.get"));
    }
}
