package per.zongwlee.issue.domain.service.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.service.BaseServiceImpl;
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
import per.zongwlee.issue.infra.utils.AgileModelMapper;
import per.zongwlee.issue.infra.utils.ConvertUtil;

import java.util.Optional;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/12
 */
@Component
@RefreshScope
public class IssueServiceImpl extends BaseServiceImpl<Issue> implements IssueService {

    @Autowired
    private IssueMapper issueMapper;

    @Autowired
    AgileModelMapper modelMapper;

    @Autowired
    UserFeignClient userFeignClient;

    @Autowired
    PriorityService priorityService;

    @Override
    public Page<IssueDTO> pageQuery(PageRequest pageRequest, Long type) {
        Issue issue = getQueryIssue(null, type);
        Page<IssueDTO> page = ConvertUtil.convertPage(PageHelper.doPageAndSort(
                pageRequest, () -> issueMapper.select(issue)), IssueDTO.class);
        return loadIssueDTOPage(page);
    }

    @Override
    public Page<IssueDTO> pageQueryBacklog(PageRequest pageRequest, Long type) {
        Issue issue = getQueryIssue(StatusEnums.backlog, type);
        Page<IssueDTO> page = ConvertUtil.convertPage(PageHelper.doPageAndSort(
                pageRequest, () -> issueMapper.select(issue)), IssueDTO.class);
        return loadIssueDTOPage(page);
    }

    @Override
    public Page<IssueDTO> pageQueryActiveMatters(PageRequest pageRequest, Long type) {
        Issue issue = getQueryIssue(StatusEnums.active, type);
        Page<IssueDTO> page = ConvertUtil.convertPage(PageHelper.doPageAndSort(
                pageRequest, () -> issueMapper.select(issue)), IssueDTO.class);
        return loadIssueDTOPage(page);
    }

    @Override
    public Page<IssueDTO> pageQueryFinishedMatters(PageRequest pageRequest, Long type) {
        Issue issue = getQueryIssue(StatusEnums.finished, type);
        Page<IssueDTO> page = ConvertUtil.convertPage(PageHelper.doPageAndSort(
                pageRequest, () -> issueMapper.select(issue)), IssueDTO.class);
        return loadIssueDTOPage(page);
    }

    @Override
    public IssueDTO queryById(Long id) {
        IssueDTO res = modelMapper.convert(issueMapper.selectByPrimaryKey(id), IssueDTO.class);
        res.setHandler(userFeignClient.queryById(res.getHandlerId()).getBody());
        res.setReporter(userFeignClient.queryById(res.getReporterId()).getBody());
        res.setPriority(modelMapper.convert(priorityService.selectByPrimaryKey(res.getPriorityId()), PriorityDTO.class));
        return res;
    }

    private Page<IssueDTO> loadIssueDTOPage(Page<IssueDTO> page) {
        page.getContent().forEach(v -> {
            Optional.ofNullable(v.getHandlerId()).ifPresent(id -> v.setHandler(userFeignClient.queryById(id).getBody()));
            v.setReporter(userFeignClient.queryById(v.getReporterId()).getBody());
            v.setPriority(modelMapper.convert(priorityService.selectByPrimaryKey(v.getPriorityId()), PriorityDTO.class));
        });
        return page;
    }

    private Issue getQueryIssue(StatusEnums status, Long type) {
        Issue issue = new Issue();
        issue.setType(type);
        Optional.ofNullable(status).ifPresent(v -> issue.setStatusId(v.getStatus()));
        return issue;
    }

    @Override
    public IssueDTO create(IssueDTO issueDTO) {
        Issue issue = modelMapper.convert(issueDTO, Issue.class);
        if (issueMapper.insert(issue) != 1)
            throw new CommonException("error.insert.issue");
        return modelMapper.convert(issueMapper.selectOne(issue), IssueDTO.class);
    }

    @Override
    public IssueDTO updateIssue(IssueDTO issueDTO) {
        Issue issue = modelMapper.convert(issueDTO, Issue.class);
        issue.setObjectVersionNumber(issueMapper.selectByPrimaryKey(issue.getId()).getObjectVersionNumber());
        if (issueMapper.updateByPrimaryKey(issue) != 1)
            throw new CommonException("error.update.issue");
        return modelMapper.convert(issueMapper.selectByPrimaryKey(issue), IssueDTO.class);
    }
}
