package per.zongwlee.issue.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.springframework.stereotype.Component;
import per.zongwlee.issue.domain.entity.Status;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/12
 */
@Component
public interface StatusMapper extends BaseMapper<Status> {
}
