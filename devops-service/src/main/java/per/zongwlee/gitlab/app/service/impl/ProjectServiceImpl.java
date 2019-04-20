package per.zongwlee.gitlab.app.service.impl;

import com.google.gson.Gson;
import io.choerodon.core.exception.CommonException;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import per.zongwlee.gitlab.app.service.GroupService;
import per.zongwlee.gitlab.app.service.ProjectService;
import per.zongwlee.gitlab.domain.entity.Repository;
import per.zongwlee.gitlab.infra.common.client.Gitlab4jClient;
import per.zongwlee.gitlab.infra.mapper.RepositoryMapper;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectServiceImpl.class);
    private Gitlab4jClient gitlab4jclient;

    private static final String GROUPNAME = "minuteF";

    @Value("${gitlab.url}")
    private String gitlabUrl;

    @Autowired
    RepositoryMapper repositoryMapper;

    @Autowired
    GroupService groupService;

    public ProjectServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    Gson gson = new Gson();

    @Override
    public Repository createProject(String projectName, String gitlabProjectName, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        Group group = groupService.queryGroupByName(GROUPNAME, 1);
        if (!groupService.listProjects(group.getId(), 1).isEmpty()) {
            throw new CommonException("error.project.can.not.be.more");
        }
        try {
            Project project = new Project();
            project.setDefaultBranch("master");
            project.setName(gitlabProjectName);
            project.setPath(gitlabProjectName);
            project.setPublic(true);
//            project.setVisibility(Visibility.PUBLIC);

            //group
            Namespace namespace = new Namespace();
            namespace.setId(group.getId());
            namespace.setFullPath(group.getFullPath());
            namespace.setPath(group.getPath());
            namespace.setName(group.getName());
//            project.setNamespace(namespace);

            Project res = gitLabApi.getProjectApi().createProject(project);

//            Project project = gitLabApi.getProjectApi().createProject(group.getId(), gitlabProjectName);
//            project.setPublic(true);
//            project.setDefaultBranch("master");
//            gitLabApi.getProjectApi().updateProject(project);

            Repository repository = new Repository();
            repository.setName(projectName);
            repository.setGitlabName(gitlabProjectName);
            repository.setGitlabId(1);
            repository.setGitlabProjectId(res.getId());
            repository.setGitlabGroupId(group.getId());
            String url = gitlabUrl + "/" + GROUPNAME + "/" + gitlabProjectName + ".git";
            repository.setUrl(url);

            if (repositoryMapper.insert(repository) != 1) {
                gitLabApi.getProjectApi().deleteProject(project);
                throw new CommonException("error.insert.repository");
            }
            return repository;
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteProject(Integer projectId) {
        try {
            gitlab4jclient
                    .getGitLabApi(1)
                    .getProjectApi().deleteProject(projectId);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage(), e);
        }
        Repository repository = new Repository();
        repository.setGitlabProjectId(projectId);
        repositoryMapper.delete(repository);
    }

    @Override
    public void deleteProjectByName(String groupName, String projectName, Integer userId) {
        try {
            Project project = null;
            try {
                project = gitlab4jclient
                        .getGitLabApi(userId)
                        .getProjectApi().getProject(groupName, projectName);
            } catch (GitLabApiException e) {
                if (e.getHttpStatus() == 404) {
                    LOGGER.info("delete not exist project: {}", e.getMessage());
                } else {
                    throw e;
                }
            }
            if (project != null) {
                gitlab4jclient
                        .getGitLabApi(userId)
                        .getProjectApi().deleteProject(project.getId());
            }
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage(), e);
        }
    }

    @Override
    public Repository getProjectById(Long projectId) {
//        try {
//            return gitlab4jclient.getGitLabApi().getProjectApi().getProject(projectId);
//        } catch (GitLabApiException e) {
//            throw new CommonException(e.getMessage(), e);
//        }
        return repositoryMapper.selectByPrimaryKey(projectId);
    }

    @Override
    public Project getProject(Integer userId, String groupCode, String projectCode) {
        try {
            return gitlab4jclient.getGitLabApi(userId).getProjectApi().getProject(groupCode, projectCode);
        } catch (GitLabApiException e) {
            if ("404 Project Not Found".equals(e.getMessage())) {
                return new Project();
            } else {
                throw new CommonException(e.getMessage(), e);
            }
        }
    }

    @Override
    public Repository updateProject(Repository newRepository, Integer userId) {
        Optional.ofNullable(newRepository.getGitlabName()).ifPresent(v -> {
            try {
                Project project = gitlab4jclient.getGitLabApi().getProjectApi().getProject(newRepository.getGitlabId());
                project.setName(newRepository.getName());
                gitlab4jclient.getGitLabApi(userId).getProjectApi().updateProject(project);
            } catch (GitLabApiException e) {
                throw new CommonException(e.getMessage(), e);
            }
        });
        if (repositoryMapper.updateByPrimaryKeySelective(newRepository) != 1) {
            throw new CommonException("error.repository.update");
        }
        return repositoryMapper.selectByPrimaryKey(newRepository.getId());
    }

    @Override
    public Member createMember(Integer userId) {
        try {
            Repository repository = new Repository();
            repository.setGitlabId(1);
            return gitlab4jclient.getGitLabApi().getProjectApi()
                    .addMember(repositoryMapper.selectOne(repository).getGitlabProjectId(),
                            userId, AccessLevel.DEVELOPER.toValue());
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteMember(Integer userId) {
        try {
            Repository repository = new Repository();
            repository.setGitlabId(1);
            gitlab4jclient.getGitLabApi().getProjectApi().removeMember(
                    repositoryMapper.selectOne(repository).getGitlabProjectId(), userId);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage(), e);
        }
    }

    @Override
    public List<Member> getAllMemberByProjectId() {
        Repository repository = new Repository();
        repository.setGitlabId(1);
        try {
            return gitlab4jclient.getGitLabApi().getProjectApi().getMembers(
                    repositoryMapper.selectOne(repository).getGitlabProjectId());
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage(), e);
        }
    }
}