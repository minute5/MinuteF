package per.zongwlee.test.manager.domain.service;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.service.BaseService;
import per.zongwlee.test.manager.api.dto.TestCaseDTO;
import per.zongwlee.test.manager.domain.entity.TestCase;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/12
 */
public interface TestCaseService extends BaseService<TestCase> {

    Page<TestCaseDTO> pageQuery(PageRequest pageRequest);

    Page<TestCaseDTO> pageQueryBacklog(PageRequest pageRequest);

    Page<TestCaseDTO> pageQueryActiveMatters(PageRequest pageRequest);

    Page<TestCaseDTO> pageQueryFinishedMatters(PageRequest pageRequest);

    TestCaseDTO queryById(Long id);

    TestCaseDTO create(TestCaseDTO testCaseDTO);

    TestCaseDTO updateCase(TestCaseDTO testCaseDTO);

}
