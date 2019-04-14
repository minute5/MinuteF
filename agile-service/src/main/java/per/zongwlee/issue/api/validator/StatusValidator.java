package per.zongwlee.issue.api.validator;

import io.choerodon.core.exception.CommonException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import per.zongwlee.issue.api.dto.PriorityDTO;
import per.zongwlee.issue.api.dto.StatusDTO;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/12
 */
@Component
public class StatusValidator {


    public void createValidate(StatusDTO statusDTO) {
        if (StringUtils.isEmpty(statusDTO.getName())) {
            throw new CommonException("error.status.create.name.empty");
        }
    }

    public void updateValidate(StatusDTO statusDTO) {
        if (statusDTO.getName() != null && statusDTO.getName().length() == 0) {
            throw new CommonException("error.status.update.name.empty");
        }

    }


}
