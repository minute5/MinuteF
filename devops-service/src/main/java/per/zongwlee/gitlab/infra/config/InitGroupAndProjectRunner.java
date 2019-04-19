package per.zongwlee.gitlab.infra.config;

import org.gitlab4j.api.models.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import per.zongwlee.gitlab.api.dto.GroupDTO;
import per.zongwlee.gitlab.app.service.GroupService;
import per.zongwlee.gitlab.app.service.ProjectService;
import per.zongwlee.gitlab.domain.entity.Repository;
import per.zongwlee.gitlab.infra.mapper.RepositoryMapper;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/16
 */
@Component
public class InitGroupAndProjectRunner implements CommandLineRunner {

    @Autowired
    GroupService groupService;

    @Autowired
    ProjectService projectService;

    @Autowired
    RepositoryMapper repositoryMapper;

    private static final String GROUPPANDROJECTNAME = "minuteF";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(String... args) {
        //root用户， id：1
        if (groupService.queryGroupByName(GROUPPANDROJECTNAME, 1) == null) {
            GroupDTO group = new GroupDTO();
            group.setName(GROUPPANDROJECTNAME);
            group.setPath(GROUPPANDROJECTNAME);
            group.setDescription("初始化Group");
            group.setVisibility(Visibility.INTERNAL);
            group.setRequestAccessEnabled(false);
            groupService.createGroup(group, 1);
        }
    }
}
