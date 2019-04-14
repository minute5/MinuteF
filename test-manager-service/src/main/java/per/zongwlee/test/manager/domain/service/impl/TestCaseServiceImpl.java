package per.zongwlee.test.manager.domain.service.impl;

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
import per.zongwlee.test.manager.api.dto.TestCaseDTO;
import per.zongwlee.test.manager.api.dto.PriorityDTO;
import per.zongwlee.test.manager.domain.entity.TestCase;
import per.zongwlee.test.manager.domain.service.PriorityService;
import per.zongwlee.test.manager.domain.service.TestCaseService;
import per.zongwlee.test.manager.infra.enums.StatusEnums;
import per.zongwlee.test.manager.infra.feign.UserFeignClient;
import per.zongwlee.test.manager.infra.mapper.IssueMapper;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/12
 */
@Component
@RefreshScope
public class TestCaseServiceImpl extends BaseServiceImpl<TestCase> implements TestCaseService {

    @Autowired
    private IssueMapper issueMapper;

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    UserFeignClient userFeignClient;

    @Autowired
    PriorityService priorityService;

    @Override
    public Page<TestCaseDTO> pageQuery(PageRequest pageRequest) {
        TestCase testCase = getQueryIssue(null);
        return ConvertPageHelper.convertPage(PageHelper.doPageAndSort(
                pageRequest, () -> issueMapper.select(testCase)), TestCaseDTO.class);
    }

    @Override
    public Page<TestCaseDTO> pageQueryBacklog(PageRequest pageRequest) {
        TestCase testCase = getQueryIssue(StatusEnums.backlog);
        Page<TestCaseDTO> page = ConvertPageHelper.convertPage(PageHelper.doPageAndSort(
                pageRequest, () -> issueMapper.select(testCase)), TestCaseDTO.class);
        return loadIssueDTOPage(page);
    }

    @Override
    public Page<TestCaseDTO> pageQueryActiveMatters(PageRequest pageRequest) {
        TestCase testCase = getQueryIssue(StatusEnums.active);
        Page<TestCaseDTO> page = ConvertPageHelper.convertPage(PageHelper.doPageAndSort(
                pageRequest, () -> issueMapper.select(testCase)), TestCaseDTO.class);
        return loadIssueDTOPage(page);
    }

    @Override
    public Page<TestCaseDTO> pageQueryFinishedMatters(PageRequest pageRequest) {
        TestCase testCase = getQueryIssue(StatusEnums.finished);
        Page<TestCaseDTO> page = ConvertPageHelper.convertPage(PageHelper.doPageAndSort(
                pageRequest, () -> issueMapper.select(testCase)), TestCaseDTO.class);
        return loadIssueDTOPage(page);
    }

    private Page<TestCaseDTO> loadIssueDTOPage(Page<TestCaseDTO> page) {
        page.getContent().forEach(v -> {
            v.setHandler(userFeignClient.queryById(v.getHandlerId()).getBody());
            v.setReporter(userFeignClient.queryById(v.getReporterId()).getBody());
            v.setPriority(modelMapper.map(priorityService.selectByPrimaryKey(v.getPriorityId()), PriorityDTO.class));
        });
        return page;
    }

    private TestCase getQueryIssue(StatusEnums status) {
        TestCase testCase = new TestCase();
        testCase.setType(1L);
        testCase.setStatusId(status.getStatus());
        return testCase;
    }

    @Override
    public TestCaseDTO create(TestCaseDTO testCaseDTO) {
        TestCase testCase = modelMapper.map(testCaseDTO, TestCase.class);
        if (issueMapper.insert(testCase) != 1)
            throw new CommonException("error.insert.testCase");
        return modelMapper.map(issueMapper.selectOne(testCase), TestCaseDTO.class);
    }

    @Override
    public TestCaseDTO updateIssue(TestCaseDTO testCaseDTO) {
        TestCase testCase = modelMapper.map(testCaseDTO, TestCase.class);
        if (issueMapper.updateByPrimaryKey(testCase) != 1)
            throw new CommonException("error.update.testCase");
        return modelMapper.map(issueMapper.selectByPrimaryKey(testCase), TestCaseDTO.class);
    }
}
