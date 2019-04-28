package per.zongwlee.gitlab.app.service.impl;

import io.choerodon.core.exception.CommonException;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.MergeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import per.zongwlee.gitlab.app.service.MergeRequestService;
import per.zongwlee.gitlab.domain.entity.Repository;
import per.zongwlee.gitlab.infra.common.client.Gitlab4jClient;
import per.zongwlee.gitlab.infra.mapper.RepositoryMapper;

import java.util.List;


@Service
public class MergeRequestServiceImpl implements MergeRequestService {

    @Value("${gitlab.url}")
    private String url;

    @Value("${gitlab.privateToken}")
    private String privateToken;

    private Gitlab4jClient gitlab4jclient;

    public MergeRequestServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Autowired
    RepositoryMapper repositoryMapper;

    @Override
    public MergeRequest createMergeRequest(Long projectId, String sourceBranch, String targetBranch,
                                           String title, String description, Integer userId) {
        Repository repository = repositoryMapper.selectByPrimaryKey(projectId);
        try {
            return gitlab4jclient.getGitLabApi(userId).getMergeRequestApi()
                    .createMergeRequest(repository.getGitlabProjectId(), sourceBranch,
                            targetBranch, title, description, null);
        } catch (GitLabApiException e) {
            throw new CommonException("error.mergeRequest.create");
        }
    }


    @Override
    public MergeRequest queryMergeRequest(Long projectId, Integer mergeRequestId, Integer userId) {
        Repository repository = repositoryMapper.selectByPrimaryKey(projectId);
        try {
            return gitlab4jclient.getGitLabApi(userId).getMergeRequestApi()
                    .getMergeRequest(repository.getGitlabProjectId(), mergeRequestId);
        } catch (GitLabApiException e) {
            throw new CommonException("error.mergeRequest.get");
        }
    }

    @Override
    public List<MergeRequest> listMergeRequests(Long projectId) {
        Repository repository = repositoryMapper.selectByPrimaryKey(projectId);
        try {
            return gitlab4jclient.getGitLabApi(null).getMergeRequestApi()
                    .getMergeRequests(repository.getGitlabProjectId());
        } catch (GitLabApiException e) {
            throw new CommonException("error.mergeRequests.list");
        }
    }

    @Override
    public MergeRequest acceptMergeRequest(Long projectId, Integer mergeRequestId,
                                           String mergeCommitMessage, Boolean shouldRemoveSourceBranch,
                                           Boolean mergeWhenPipelineSucceeds, Integer userId) {
        Repository repository = repositoryMapper.selectByPrimaryKey(projectId);
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getMergeRequestApi()
                    .acceptMergeRequest(repository.getGitlabProjectId(),
                            mergeRequestId,
                            mergeCommitMessage,
                            shouldRemoveSourceBranch,
                            mergeWhenPipelineSucceeds);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage(), e);
        }
    }

    @Override
    public List<Commit> listCommits(Long projectId, Integer mergeRequestId, Integer userId) {
        Repository repository = repositoryMapper.selectByPrimaryKey(projectId);
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getMergeRequestApi().getCommits(repository.getGitlabProjectId(), mergeRequestId);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteMergeRequest(Long projectId, Integer mergeRequestId) {
        Repository repository = repositoryMapper.selectByPrimaryKey(projectId);
        try {
            gitlab4jclient.getGitLabApi()
                    .getMergeRequestApi().deleteMergeRequest(repository.getGitlabProjectId(), mergeRequestId);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage(), e);
        }
    }
}
