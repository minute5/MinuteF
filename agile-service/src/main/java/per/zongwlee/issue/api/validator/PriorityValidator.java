package per.zongwlee.issue.api.validator;

import per.zongwlee.issue.api.dto.PriorityDTO;
import io.choerodon.core.exception.CommonException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/12
 */
@Component
public class PriorityValidator {


    public void createValidate(PriorityDTO priorityDTO) {
        if (StringUtils.isEmpty(priorityDTO.getName())) {
            throw new CommonException("error.priority.create.name.empty");
        }
    }

    public void updateValidate(PriorityDTO priorityDTO) {
        if (priorityDTO.getName() != null && priorityDTO.getName().length() == 0) {
            throw new CommonException("error.priority.update.name.empty");
        }

    }


}
