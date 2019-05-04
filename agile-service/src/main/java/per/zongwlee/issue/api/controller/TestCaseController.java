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
import per.zongwlee.issue.domain.service.IssueService;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/12
 */
@RestController
@RequestMapping(value = "/v1/case")
public class TestCaseController extends BaseController {

    private static final ModelMapper modelMapper = new ModelMapper();

    private static final Long TYPE = 1L;

    @Autowired
    private IssueService issueService;

    @ApiOperation(value = "创建测试用例")
    @PostMapping
    public ResponseEntity<IssueDTO> create(@RequestBody IssueDTO issueDTO) {
        return new ResponseEntity<>(issueService.create(issueDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "删除测试用例")
    @DeleteMapping(value = "/{case_id}")
    public ResponseEntity<Boolean> delete(@PathVariable("case_id") Long caseId) {
        return new ResponseEntity<>(issueService.deleteByPrimaryKey(caseId) == 1, HttpStatus.OK);
    }

    @ApiOperation(value = "更新测试用例")
    @PutMapping
    public ResponseEntity<IssueDTO> update(@RequestBody @Valid IssueDTO issueDTO) {
        return new ResponseEntity<>(issueService.updateIssue(issueDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "查询所有测试用例")
    @GetMapping("/list/all")
    public ResponseEntity<Page<IssueDTO>> queryAll(
            @ApiParam(value = "分页参数") PageRequest pageRequest) {
        return Optional.ofNullable(issueService.pageQuery(pageRequest, TYPE))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.case.list.get"));
    }

    @ApiOperation(value = "查询待办测试用例")
    @GetMapping("/list/backlog")
    public ResponseEntity<Page<IssueDTO>> queryBacklogs(
            @ApiParam(value = "分页参数") PageRequest pageRequest) {
        return Optional.ofNullable(issueService.pageQueryBacklog(pageRequest, TYPE))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.case.list.get"));
    }

    @ApiOperation(value = "查询活跃测试用例")
    @GetMapping("/list/active")
    public ResponseEntity<Page<IssueDTO>> queryActiveMatters(
            @ApiParam(value = "分页参数") PageRequest pageRequest) {
        return Optional.ofNullable(issueService.pageQueryActiveMatters(pageRequest, TYPE))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.case.list.get"));
    }

    @ApiOperation(value = "查询完成测试用例")
    @GetMapping("/list/finished")
    public ResponseEntity<Page<IssueDTO>> queryFinishedMatters(
            @ApiParam(value = "分页参数") PageRequest pageRequest) {
        return Optional.ofNullable(issueService.pageQueryFinishedMatters(pageRequest, TYPE))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.case.list.get"));
    }

    @ApiOperation(value = "查询完成测试用例")
    @GetMapping("/list/failed")
    public ResponseEntity<Page<IssueDTO>> queryFailedMatters(
            @ApiParam(value = "分页参数") PageRequest pageRequest) {
        return Optional.ofNullable(issueService.queryFailedMatters(pageRequest, TYPE))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.case.list.get"));
    }

    @ApiOperation(value = "根据id查询测试用例")
    @GetMapping("/{case_id}")
    public ResponseEntity<IssueDTO> queryById(@ApiParam(value = "id", required = true)
                                              @PathVariable Long caseId) {
        return Optional.ofNullable(modelMapper.map(issueService.queryById(caseId), IssueDTO.class))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.priority.get"));

    }
}
