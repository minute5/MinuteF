package io.choerodon.test.manager.app.service;

import java.util.List;

/**
 * Created by 842767365@qq.com on 6/25/18.
 */
public interface TestStatusService {
    List<TestStatusDTO> query(TestStatusDTO testStatusDTO);

    TestStatusDTO insert(TestStatusDTO testStatusDTO);

    void delete(TestStatusDTO testStatusDTO);

    TestStatusDTO update(TestStatusDTO testStatusDTO);

    void populateStatus(TestCycleCaseDTO testCycleCaseDTO);

    Long getDefaultStatusId(String type);
}
