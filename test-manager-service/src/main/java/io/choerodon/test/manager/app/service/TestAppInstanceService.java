package io.choerodon.test.manager.app.service;

import io.choerodon.asgard.api.dto.QuartzTask;
import io.choerodon.asgard.api.dto.ScheduleTaskDTO;
import io.choerodon.devops.api.dto.ReplaceResult;

import java.util.Map;

/**
 * Created by zongw.lee@gmail.com on 22/11/2018
 */
public interface TestAppInstanceService {

    TestAppInstanceDTO create(ApplicationDeployDTO deployDTO, Long projectId, Long userId);

    void createBySchedule(Map<String, Object> data);

    void closeInstance(String releaseNames, Long status, String logFile);

    QuartzTask createTimedTaskForDeploy(ScheduleTaskDTO taskDTO, Long projectId);

    ReplaceResult queryValues(Long projectId, Long appId, Long envId, Long appVersionId);

    void updateInstance(String releaseNames, String podName, String conName);

    void shutdownInstance(Long instanceId);
}
