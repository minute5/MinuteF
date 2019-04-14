package per.zongwlee.issue.domain.service.impl;

import io.choerodon.core.convertor.ConvertPageHelper;
import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.service.BaseServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import per.zongwlee.issue.api.dto.IssueDTO;
import per.zongwlee.issue.api.dto.PriorityDTO;
import per.zongwlee.issue.domain.entity.Issue;
import per.zongwlee.issue.domain.service.IssueService;
import per.zongwlee.issue.domain.service.PriorityService;
import per.zongwlee.issue.infra.enums.StatusEnums;
import per.zongwlee.issue.infra.feign.UserFeignClient;
import per.zongwlee.issue.infra.mapper.IssueMapper;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/12
 */
@Component
@RefreshScope
public class IssueServiceImpl extends BaseServiceImpl<Issue> implements IssueService {

    @Autowired
    private IssueMapper issueMapper;

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    UserFeignClient userFeignClient;

    @Autowired
    PriorityService priorityService;

    @Override
    public Page<IssueDTO> pageQuery(PageRequest pageRequest) {
        Issue issue = getQueryIssue(null);
        return ConvertPageHelper.convertPage(PageHelper.doPageAndSort(
                pageRequest, () -> issueMapper.select(issue)), IssueDTO.class);
    }

    @Override
    public Page<IssueDTO> pageQueryBacklog(PageRequest pageRequest) {
        Issue issue = getQueryIssue(StatusEnums.backlog);
        Page<IssueDTO> page = ConvertPageHelper.convertPage(PageHelper.doPageAndSort(
                pageRequest, () -> issueMapper.select(issue)), IssueDTO.class);
        return loadIssueDTOPage(page);
    }

    @Override
    public Page<IssueDTO> pageQueryActiveMatters(PageRequest pageRequest) {
        Issue issue = getQueryIssue(StatusEnums.active);
        Page<IssueDTO> page = ConvertPageHelper.convertPage(PageHelper.doPageAndSort(
                pageRequest, () -> issueMapper.select(issue)), IssueDTO.class);
        return loadIssueDTOPage(page);
    }

    @Override
    public Page<IssueDTO> pageQueryFinishedMatters(PageRequest pageRequest) {
        Issue issue = getQueryIssue(StatusEnums.finished);
        Page<IssueDTO> page = ConvertPageHelper.convertPage(PageHelper.doPageAndSort(
                pageRequest, () -> issueMapper.select(issue)), IssueDTO.class);
        return loadIssueDTOPage(page);
    }

    private Page<IssueDTO> loadIssueDTOPage(Page<IssueDTO> page) {
        page.getContent().forEach(v -> {
            v.setHandler(userFeignClient.queryById(v.getHandlerId()).getBody());
            v.setReporter(userFeignClient.queryById(v.getReporterId()).getBody());
            v.setPriority(modelMapper.map(priorityService.selectByPrimaryKey(v.getPriorityId()), PriorityDTO.class));
        });
        return page;
    }

    private Issue getQueryIssue(StatusEnums status) {
        Issue issue = new Issue();
        issue.setType(0L);
        issue.setStatusId(status.getStatus());
        return issue;
    }

    @Override
    public IssueDTO create(IssueDTO issueDTO) {
        Issue issue = modelMapper.map(issueDTO, Issue.class);
        if (issueMapper.insert(issue) != 1)
            throw new CommonException("error.insert.issue");
        return modelMapper.map(issueMapper.selectOne(issue), IssueDTO.class);
    }

    @Override
    public IssueDTO updateIssue(IssueDTO issueDTO) {
        Issue issue = modelMapper.map(issueDTO, Issue.class);
        if (issueMapper.updateByPrimaryKey(issue) != 1)
            throw new CommonException("error.update.issue");
        return modelMapper.map(issueMapper.selectByPrimaryKey(issue), IssueDTO.class);
    }
}
