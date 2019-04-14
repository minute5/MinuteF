package per.zongwlee.test.manager.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.springframework.stereotype.Component;
import per.zongwlee.test.manager.domain.entity.Priority;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/12
 */
@Component
public interface PriorityMapper extends BaseMapper<Priority> {
}
