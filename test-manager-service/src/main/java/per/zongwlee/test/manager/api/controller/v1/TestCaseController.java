package per.zongwlee.test.manager.api.controller.v1;

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
import per.zongwlee.test.manager.api.dto.TestCaseDTO;
import per.zongwlee.test.manager.domain.service.TestCaseService;

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

    @Autowired
    private TestCaseService testCaseService;

    @ApiOperation(value = "创建测试用例")
    @PostMapping
    public ResponseEntity<TestCaseDTO> create(@RequestBody TestCaseDTO testCaseDTO) {
        return new ResponseEntity<>(testCaseService.create(testCaseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "删除测试用例")
    @DeleteMapping(value = "/{case_id}")
    public ResponseEntity<Boolean> delete(@PathVariable("case_id") Long caseId) {
        return new ResponseEntity<>(testCaseService.deleteByPrimaryKey(caseId) == 1, HttpStatus.OK);
    }

    @ApiOperation(value = "更新测试用例")
    @PutMapping
    public ResponseEntity<TestCaseDTO> update(@RequestBody @Valid TestCaseDTO testCaseDTO) {
        return new ResponseEntity<>(testCaseService.updateCase(testCaseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "查询所有测试用例")
    @GetMapping("/list/all")
    public ResponseEntity<Page<TestCaseDTO>> queryAll(
            @ApiParam(value = "分页参数") PageRequest pageRequest) {
        return Optional.ofNullable(testCaseService.pageQuery(pageRequest))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.case.list.get"));
    }

    @ApiOperation(value = "查询待办测试用例")
    @GetMapping("/list/backlog")
    public ResponseEntity<Page<TestCaseDTO>> queryBacklogs(
            @ApiParam(value = "分页参数") PageRequest pageRequest) {
        return Optional.ofNullable(testCaseService.pageQueryBacklog(pageRequest))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.case.list.get"));
    }

    @ApiOperation(value = "查询活跃测试用例")
    @GetMapping("/list/active")
    public ResponseEntity<Page<TestCaseDTO>> queryActiveMatters(
            @ApiParam(value = "分页参数") PageRequest pageRequest) {
        return Optional.ofNullable(testCaseService.pageQueryActiveMatters(pageRequest))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.case.list.get"));
    }

    @ApiOperation(value = "查询完成测试用例")
    @GetMapping("/list/finished")
    public ResponseEntity<Page<TestCaseDTO>> queryFinishedMatters(
            @ApiParam(value = "分页参数") PageRequest pageRequest) {
        return Optional.ofNullable(testCaseService.pageQueryFinishedMatters(pageRequest))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.case.list.get"));
    }

    @ApiOperation(value = "根据id查询测试用例")
    @GetMapping("/{case_id}")
    public ResponseEntity<TestCaseDTO> queryById(@ApiParam(value = "id", required = true)
                                                 @PathVariable Long caseId) {
        return Optional.ofNullable(modelMapper.map(testCaseService.queryById(caseId), TestCaseDTO.class))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.priority.get"));

    }
}
