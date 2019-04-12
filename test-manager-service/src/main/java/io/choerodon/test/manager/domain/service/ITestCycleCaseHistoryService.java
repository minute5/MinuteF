package io.choerodon.test.manager.domain.service;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.test.manager.domain.test.manager.entity.TestCycleCaseHistoryE;

/**
 * Created by 842767365@qq.com on 6/11/18.
 */
public interface ITestCycleCaseHistoryService {
    TestCycleCaseHistoryE insert(TestCycleCaseHistoryE testCycleCaseHistoryE);

    Page<TestCycleCaseHistoryE> query(TestCycleCaseHistoryE testCycleCaseHistoryE, PageRequest pageRequest);
}
