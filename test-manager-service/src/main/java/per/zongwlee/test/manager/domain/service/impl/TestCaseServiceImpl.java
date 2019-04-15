package per.zongwlee.test.manager.domain.service.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import per.zongwlee.test.manager.api.dto.TestCaseDTO;
import per.zongwlee.test.manager.api.dto.PriorityDTO;
import per.zongwlee.test.manager.domain.entity.TestCase;
import per.zongwlee.test.manager.domain.service.PriorityService;
import per.zongwlee.test.manager.domain.service.TestCaseService;
import per.zongwlee.test.manager.infra.common.utils.ConvertUtil;
import per.zongwlee.test.manager.infra.common.utils.TestCaseModelMapper;
import per.zongwlee.test.manager.infra.enums.StatusEnums;
import per.zongwlee.test.manager.infra.feign.UserFeignClient;
import per.zongwlee.test.manager.infra.mapper.TestCaseMapper;

import java.util.Optional;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/12
 */
@Component
@RefreshScope
public class TestCaseServiceImpl extends BaseServiceImpl<TestCase> implements TestCaseService {

    @Autowired
    private TestCaseMapper testCaseMapper;

    @Autowired
    TestCaseModelMapper modelMapper;

    @Autowired
    UserFeignClient userFeignClient;

    @Autowired
    PriorityService priorityService;

    @Override
    public Page<TestCaseDTO> pageQuery(PageRequest pageRequest) {
        TestCase testCase = getQueryCase(null);
        return ConvertUtil.convertPage(PageHelper.doPageAndSort(
                pageRequest, () -> testCaseMapper.select(testCase)), TestCaseDTO.class);
    }

    @Override
    public Page<TestCaseDTO> pageQueryBacklog(PageRequest pageRequest) {
        TestCase testCase = getQueryCase(StatusEnums.backlog);
        Page<TestCaseDTO> page = ConvertUtil.convertPage(PageHelper.doPageAndSort(
                pageRequest, () -> testCaseMapper.select(testCase)), TestCaseDTO.class);
        return loadCaseDTOPage(page);
    }

    @Override
    public Page<TestCaseDTO> pageQueryActiveMatters(PageRequest pageRequest) {
        TestCase testCase = getQueryCase(StatusEnums.active);
        Page<TestCaseDTO> page = ConvertUtil.convertPage(PageHelper.doPageAndSort(
                pageRequest, () -> testCaseMapper.select(testCase)), TestCaseDTO.class);
        return loadCaseDTOPage(page);
    }

    @Override
    public Page<TestCaseDTO> pageQueryFinishedMatters(PageRequest pageRequest) {
        TestCase testCase = getQueryCase(StatusEnums.finished);
        Page<TestCaseDTO> page = ConvertUtil.convertPage(PageHelper.doPageAndSort(
                pageRequest, () -> testCaseMapper.select(testCase)), TestCaseDTO.class);
        return loadCaseDTOPage(page);
    }

    @Override
    public TestCaseDTO queryById(Long id) {
        TestCaseDTO res = modelMapper.convert(testCaseMapper.selectByPrimaryKey(id), TestCaseDTO.class);
        res.setHandler(userFeignClient.queryById(res.getHandlerId()).getBody());
        res.setReporter(userFeignClient.queryById(res.getReporterId()).getBody());
        res.setPriority(modelMapper.convert(priorityService.selectByPrimaryKey(res.getPriorityId()), PriorityDTO.class));
        return res;
    }

    private Page<TestCaseDTO> loadCaseDTOPage(Page<TestCaseDTO> page) {
        page.getContent().forEach(v -> {
            v.setHandler(userFeignClient.queryById(v.getHandlerId()).getBody());
            v.setReporter(userFeignClient.queryById(v.getReporterId()).getBody());
            v.setPriority(modelMapper.convert(priorityService.selectByPrimaryKey(v.getPriorityId()), PriorityDTO.class));
        });
        return page;
    }

    private TestCase getQueryCase(StatusEnums status) {
        TestCase testCase = new TestCase();
        testCase.setType(1L);
        Optional.ofNullable(status).ifPresent(v -> testCase.setStatusId(v.getStatus()));
        return testCase;
    }

    @Override
    public TestCaseDTO create(TestCaseDTO testCaseDTO) {
        TestCase testCase = modelMapper.convert(testCaseDTO, TestCase.class);
        if (testCaseMapper.insert(testCase) != 1)
            throw new CommonException("error.insert.testCase");
        return modelMapper.convert(testCaseMapper.selectOne(testCase), TestCaseDTO.class);
    }

    @Override
    public TestCaseDTO updateCase(TestCaseDTO testCaseDTO) {
        TestCase testCase = modelMapper.convert(testCaseDTO, TestCase.class);
        testCase.setObjectVersionNumber(testCaseMapper.selectByPrimaryKey(testCase.getId()).getObjectVersionNumber());
        if (testCaseMapper.updateByPrimaryKey(testCase) != 1)
            throw new CommonException("error.update.testCase");
        return modelMapper.convert(testCaseMapper.selectByPrimaryKey(testCase), TestCaseDTO.class);
    }
}
