package io.choerodon.issue.api.controller;

import io.choerodon.core.base.BaseController;
import io.choerodon.issue.api.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shinan.chen
 * @date 2018/9/3
 */

@RestController
@RequestMapping(value = "/v1/projects/{project_id}/issue")
public class IssueController extends BaseController {

    @Autowired
    private IssueService issueService;


}
