package io.choerodon.test.manager.app.service.impl;

import io.choerodon.asgard.api.dto.QuartzTask;
import io.choerodon.asgard.api.dto.ScheduleMethodDTO;
import io.choerodon.asgard.api.dto.ScheduleTaskDTO;
import io.choerodon.test.manager.app.service.ScheduleService;
import io.choerodon.test.manager.infra.feign.ScheduleFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zongw.lee@gmail.com on 23/11/2018
 */
@Component
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    ScheduleFeignClient feignClient;

    @Override
    public QuartzTask create(long projectId, ScheduleTaskDTO dto) {
        return feignClient.create(projectId, dto).getBody();
    }

    @Override
    public List<ScheduleMethodDTO> getMethodByService(long projectId, String service) {
        return feignClient.getMethodByService(projectId,service).getBody();
    }
}
