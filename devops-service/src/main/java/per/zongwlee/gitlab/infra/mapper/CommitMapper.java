package per.zongwlee.gitlab.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.springframework.stereotype.Component;
import per.zongwlee.gitlab.domain.entity.Commit;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/15
 */
@Component
public interface CommitMapper extends BaseMapper<Commit> {
}
