package per.zongwlee.test.manager.domain.service.impl;

import io.choerodon.mybatis.service.BaseServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import per.zongwlee.test.manager.domain.entity.Priority;
import per.zongwlee.test.manager.domain.service.PriorityService;
import per.zongwlee.test.manager.infra.mapper.PriorityMapper;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/12
 */
@Service
@RefreshScope
public class PriorityServiceImpl extends BaseServiceImpl<Priority> implements PriorityService {

    @Autowired
    private PriorityMapper priorityMapper;

    private ModelMapper modelMapper = new ModelMapper();


}
