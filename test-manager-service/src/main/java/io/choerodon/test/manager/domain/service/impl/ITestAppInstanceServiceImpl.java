package io.choerodon.test.manager.domain.service.impl;

import io.choerodon.core.exception.CommonException;
import io.choerodon.test.manager.domain.service.ITestAppInstanceService;
import io.choerodon.test.manager.domain.test.manager.entity.TestAppInstanceE;
import io.choerodon.test.manager.infra.mapper.TestAppInstanceMapper;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ITestAppInstanceServiceImpl implements ITestAppInstanceService {
    
    @Autowired
    TestAppInstanceMapper mapper;

    @Override
    public List<TestAppInstanceE> queryDelayInstance(int delayTime) {
        Date delayTiming=DateUtils.addMinutes(new Date(),-delayTime);
        return mapper.queryDelayInstance(delayTiming);
    }

    @Override
    public String queryValueByEnvIdAndAppId(Long envId, Long appId) {
        return mapper.queryValueByEnvIdAndAppId(envId,appId);
    }

    @Override
    public TestAppInstanceE update(TestAppInstanceE testAppInstanceE) {
        if(mapper.updateByPrimaryKeySelective(testAppInstanceE)==0){
            throw new CommonException("error.ItestAppInstanceEServiceImpl.update");
        }
        return mapper.selectByPrimaryKey(testAppInstanceE.getId());
    }

    @Override
    public void delete(TestAppInstanceE testAppInstanceE) {
        mapper.delete(testAppInstanceE);
    }

    @Override
    public TestAppInstanceE insert(TestAppInstanceE testAppInstanceE) {
        if(mapper.insert(testAppInstanceE)==0){
            throw new CommonException("error.ITestAppInstanceServiceImpl.insert");
        }
        return mapper.selectByPrimaryKey(testAppInstanceE.getId());
    }

    @Override
    public TestAppInstanceE queryOne(TestAppInstanceE id){
       return mapper.selectOne(id);
    }

    @Override
    public void updateInstanceWithoutStatus(TestAppInstanceE testAppInstanceE){
        mapper.updateInstanceWithoutStatus(testAppInstanceE);
    }

    @Override
    public void updateStatus(TestAppInstanceE testAppInstanceE){
        mapper.updateStatus(testAppInstanceE);
    }

    @Override
    public void closeInstance(TestAppInstanceE testAppInstanceE){
        mapper.closeInstance(testAppInstanceE);
    }

}
