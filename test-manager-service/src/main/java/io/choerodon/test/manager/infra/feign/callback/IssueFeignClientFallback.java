package io.choerodon.test.manager.infra.feign.callback;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import io.choerodon.agile.api.dto.IssueTypeDTO;
import io.choerodon.agile.api.dto.PriorityDTO;
import io.choerodon.core.exception.CommonException;
import io.choerodon.test.manager.infra.feign.IssueFeignClient;

@Component
public class IssueFeignClientFallback implements IssueFeignClient {
    @Override
    public ResponseEntity<List<IssueTypeDTO>> queryIssueType(Long projectId, String applyType, Long organizationId) {
        throw new CommonException("error.query.issue.type");
    }

    @Override
    public ResponseEntity<List<PriorityDTO>> queryPriorityId(Long projectId, Long organizationId) {
        throw new CommonException("error.query.priority.id");
    }
}
