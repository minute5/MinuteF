package per.zongwlee.issue.domain.service;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.service.BaseService;
import per.zongwlee.issue.api.dto.IssueDTO;
import per.zongwlee.issue.domain.entity.Issue;

import java.util.List;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/12
 */
public interface IssueService extends BaseService<Issue> {

    //0是issue，1是testCase
    Page<IssueDTO> pageQuery(PageRequest pageRequest, Long type);

    Page<IssueDTO> pageQueryBacklog(PageRequest pageRequest, Long type);

    Page<IssueDTO> pageQueryActiveMatters(PageRequest pageRequest, Long type);

    Page<IssueDTO> pageQueryFinishedMatters(PageRequest pageRequest, Long type);

    Page<IssueDTO> queryFailedMatters(PageRequest pageRequest, Long type);

    List<IssueDTO> queryMatters();

    IssueDTO queryById(Long id);

    IssueDTO queryByBranchId(Long branchId);

    IssueDTO create(IssueDTO issueDTO);

    IssueDTO updateIssue(IssueDTO issueDTO);

}
