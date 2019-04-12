package per.zongwlee.gitlab.app.assember;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import io.choerodon.core.convertor.ConvertorI;
import per.zongwlee.gitlab.api.dto.GitlabProjectEventDTO;
import per.zongwlee.gitlab.domain.event.GitlabProjectEventPayload;

@Component
public class GitlabProjectEventAssember implements ConvertorI<GitlabProjectEventPayload, Object, GitlabProjectEventDTO> {

    @Override
    public GitlabProjectEventPayload dtoToEntity(GitlabProjectEventDTO gitlabProjectEventDTO){

        GitlabProjectEventPayload gitlabProjectEventPayload = new GitlabProjectEventPayload();
        BeanUtils.copyProperties(gitlabProjectEventDTO, gitlabProjectEventPayload);
        return gitlabProjectEventPayload;
    }

}
