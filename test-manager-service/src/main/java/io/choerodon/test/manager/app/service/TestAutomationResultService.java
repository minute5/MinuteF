package io.choerodon.test.manager.app.service;

import java.util.List;

public interface TestAutomationResultService {
    List<TestAutomationResultDTO> query(TestAutomationResultDTO testAutomationResultDTO);

    TestAutomationResultDTO changeAutomationResult(TestAutomationResultDTO testAutomationResultDTO, Long projectId);

    void removeAutomationResult(TestAutomationResultDTO testAutomationResultDTO);
}
