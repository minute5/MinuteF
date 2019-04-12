package io.choerodon.test.manager.infra.repository.impl;

import io.choerodon.core.convertor.ConvertHelper;
import io.choerodon.core.convertor.ConvertPageHelper;
import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.test.manager.domain.repository.TestCycleCaseHistoryRepository;
import io.choerodon.test.manager.domain.test.manager.entity.TestCycleCaseHistoryE;
import io.choerodon.test.manager.infra.common.utils.DBValidateUtil;
import io.choerodon.test.manager.infra.dataobject.TestCycleCaseAttachmentRelDO;
import io.choerodon.test.manager.infra.dataobject.TestCycleCaseHistoryDO;
import io.choerodon.test.manager.infra.mapper.TestCycleCaseHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by 842767365@qq.com on 6/11/18.
 */
@Component
public class TestCycleCaseHistoryRepositoryImpl implements TestCycleCaseHistoryRepository {
    @Autowired
    TestCycleCaseHistoryMapper testCycleCaseHistoryMapper;

    @Override
    public TestCycleCaseHistoryE insert(TestCycleCaseHistoryE testCycleCaseHistoryE) {
        TestCycleCaseHistoryDO convert = ConvertHelper.convert(testCycleCaseHistoryE, TestCycleCaseHistoryDO.class);
        DBValidateUtil.executeAndvalidateUpdateNum(testCycleCaseHistoryMapper::insert,convert,1,"error.history.insert");
        return ConvertHelper.convert(convert, TestCycleCaseHistoryE.class);
    }

    @Override
    public Page<TestCycleCaseHistoryE> query(TestCycleCaseHistoryE testCycleCaseHistoryE, PageRequest pageRequest) {
        TestCycleCaseHistoryDO convert = ConvertHelper.convert(testCycleCaseHistoryE, TestCycleCaseHistoryDO.class);

        Page<TestCycleCaseAttachmentRelDO> serviceDOPage = PageHelper.doPageAndSort(pageRequest,
                () -> testCycleCaseHistoryMapper.query(convert));

        return ConvertPageHelper.convertPage(serviceDOPage, TestCycleCaseHistoryE.class);
    }

}
