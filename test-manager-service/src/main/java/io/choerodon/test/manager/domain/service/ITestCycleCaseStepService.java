package io.choerodon.test.manager.domain.service;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.test.manager.domain.test.manager.entity.TestCycleCaseE;
import io.choerodon.test.manager.domain.test.manager.entity.TestCycleCaseStepE;

import java.util.List;

/**
 * Created by 842767365@qq.com on 6/11/18.
 */
public interface ITestCycleCaseStepService {


    List<TestCycleCaseStepE> update(List<TestCycleCaseStepE> testCycleCaseStepE);

    /**
     * 查询循环测试下所有步骤
     *
     * @param testCycleCaseE
     * @return
     */
	Page<TestCycleCaseStepE> querySubStep(TestCycleCaseE testCycleCaseE, PageRequest pageRequest, Long projectId);

    /**
     * 启动循环测试下所有步骤
     *
     * @param testCycleCaseE
     */
	void createTestCycleCaseStep(TestCycleCaseE testCycleCaseE, Long projectId);

    /**
     * 删除CycleCase下所有Step
     *
     * @param testCycleCaseE
     */
    void deleteByTestCycleCase(TestCycleCaseE testCycleCaseE);

    /**
     * 删除一个Step
     *
     * @param testCycleCaseStepE
     */
    void deleteStep(TestCycleCaseStepE testCycleCaseStepE);

}
