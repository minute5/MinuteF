package per.zongwlee.test.manager.domain.service.impl;

import io.choerodon.mybatis.service.BaseServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import per.zongwlee.test.manager.domain.entity.Status;
import per.zongwlee.test.manager.domain.service.StatusService;
import per.zongwlee.test.manager.infra.mapper.StatusMapper;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/12
 */
@Service
@RefreshScope
public class StatusServiceImpl extends BaseServiceImpl<Status> implements StatusService {

    @Autowired
    private StatusMapper statusMapper;

    private ModelMapper modelMapper = new ModelMapper();


}
