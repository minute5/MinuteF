package per.zongwlee.issue.api.controller;

import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.zongwlee.issue.api.dto.IssueDTO;
import per.zongwlee.issue.domain.entity.Priority;
import per.zongwlee.issue.domain.service.PriorityService;

import java.util.List;
import java.util.Optional;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/12
 */
@RestController
@RequestMapping(value = "/v1/priority")
public class PriorityController {

    @Autowired
    private PriorityService priorityService;

    @ApiOperation(value = "查询所有优先级")
    @GetMapping("/list/all")
    public ResponseEntity<List<Priority>> queryAll() {
        return Optional.ofNullable(priorityService.selectAll())
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.priority.list.get"));
    }
}
