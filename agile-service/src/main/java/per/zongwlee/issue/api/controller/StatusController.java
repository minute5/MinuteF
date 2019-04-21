package per.zongwlee.issue.api.controller;

import io.choerodon.core.exception.CommonException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.zongwlee.issue.domain.entity.Status;
import per.zongwlee.issue.domain.service.StatusService;

import java.util.List;
import java.util.Optional;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/12
 */
@RestController
@RequestMapping(value = "/v1/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @ApiOperation(value = "查询所有状态")
    @GetMapping("/list/all")
    public ResponseEntity<List<Status>> queryAll() {
        return Optional.ofNullable(statusService.selectAll())
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.status.list.get"));
    }
}
