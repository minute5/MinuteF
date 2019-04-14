package per.zongwlee.issue.domain.service;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.service.BaseService;
import per.zongwlee.issue.api.dto.IssueDTO;
import per.zongwlee.issue.domain.entity.Issue;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/12
 */
public interface IssueService extends BaseService<Issue> {

    Page<IssueDTO> pageQuery(PageRequest pageRequest);

    Page<IssueDTO> pageQueryBacklog(PageRequest pageRequest);

    Page<IssueDTO> pageQueryActiveMatters(PageRequest pageRequest);

    Page<IssueDTO> pageQueryFinishedMatters(PageRequest pageRequest);

    IssueDTO queryById(Long id);

    IssueDTO create(IssueDTO issueDTO);

    IssueDTO updateIssue(IssueDTO issueDTO);

}
