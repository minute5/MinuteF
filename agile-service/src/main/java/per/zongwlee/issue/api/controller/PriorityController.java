package per.zongwlee.issue.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.zongwlee.issue.domain.service.PriorityService;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/12
 */
@RestController
@RequestMapping(value = "/v1/priority")
public class PriorityController {

    @Autowired
    private PriorityService priorityService;


}
