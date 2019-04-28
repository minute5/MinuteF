package per.zongwlee.gitlab.app.service.impl;

import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.zongwlee.gitlab.app.service.RepositoryService;
import per.zongwlee.gitlab.domain.entity.Branch;
import per.zongwlee.gitlab.domain.entity.Repository;
import per.zongwlee.gitlab.infra.common.client.Gitlab4jClient;
import per.zongwlee.gitlab.infra.feign.AgileFeignClient;
import per.zongwlee.gitlab.infra.mapper.BranchMapper;
import per.zongwlee.gitlab.infra.mapper.RepositoryMapper;

import java.util.List;


@Service
public class RepositoryServiceImpl implements RepositoryService {

    private Gitlab4jClient gitlab4jclient;

    public RepositoryServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Autowired
    BranchMapper branchMapper;

    @Autowired
    RepositoryMapper repositoryMapper;

    @Autowired
    AgileFeignClient agileFeignClient;

    @Override
    public Branch createBranch(Long projectId, String branchName, String source, Integer userId, Long issueId) {
        Repository repository = repositoryMapper.selectByPrimaryKey(projectId);
        try {
            this.gitlab4jclient.getGitLabApi(userId).getRepositoryApi()
                    .createBranch(repository.getGitlabProjectId(), branchName, source);
        } catch (GitLabApiException e) {
            if (e.getMessage().equals("Branch already exists")) {
                Branch branch2 = new Branch();
                branch2.setName("create branch message:Branch already exists");
                return branch2;
            }
            throw new CommonException(e.getMessage(), e);
        }
        Branch branch = new Branch();
        branch.setName(branchName);
        branch.setSourceName(source);
        branch.setCreatorId(userId);
        branch.setActive(0);
        branch.setProjectId(repository.getGitlabProjectId());
        if (branchMapper.insert(branch) != 1) {
            throw new CommonException("error.branch.insert");
        }
        return branchMapper.selectOne(branch);
    }

    @Override
    public List<Tag> listTags(Long projectId, Integer userId) {
        Repository repository = repositoryMapper.selectByPrimaryKey(projectId);
        try {
            return gitlab4jclient.getGitLabApi(userId).getRepositoryApi().getTags(repository.getGitlabProjectId());
        } catch (GitLabApiException e) {
            throw new CommonException("error.tag.get");
        }
    }

    @Override
    public List<Tag> listTagsByPage(Long projectId, PageRequest pageRequest, Integer userId) {
        Repository repository = repositoryMapper.selectByPrimaryKey(projectId);
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getRepositoryApi()
                    .getTags(repository.getGitlabProjectId(), pageRequest.getPage(), pageRequest.getSize());
        } catch (GitLabApiException e) {
            throw new CommonException("error.tag.getPage");
        }
    }

    @Override
    public Tag createTag(Long projectId, String tagName, String ref, String msg, String releaseNotes, Integer userId) {
        Repository repository = repositoryMapper.selectByPrimaryKey(projectId);
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getRepositoryApi()
                    .createTag(repository.getGitlabProjectId(), tagName, ref, msg, releaseNotes);
        } catch (GitLabApiException e) {
            throw new CommonException("error.tag.create: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteTag(Long projectId, String tagName, Integer userId) {
        Repository repository = repositoryMapper.selectByPrimaryKey(projectId);
        try {
            gitlab4jclient
                    .getGitLabApi(userId)
                    .getRepositoryApi()
                    .deleteTag(repository.getGitlabProjectId(), tagName);
        } catch (GitLabApiException e) {
            throw new CommonException("error.tag.delete: " + e.getMessage(), e);
        }
    }

    @Override
    public Tag updateTagRelease(Long projectId, String name, String releaseNotes, Integer userId) {
        Repository repository = repositoryMapper.selectByPrimaryKey(projectId);
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getRepositoryApi()
                    .updateTagRelease(repository.getGitlabProjectId(), name, releaseNotes);
        } catch (GitLabApiException e) {
            throw new CommonException("error.tag.update: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteBranch(Long projectId, String branchName, Integer userId) {
        Repository repository = repositoryMapper.selectByPrimaryKey(projectId);

        Branch branch = new Branch();
        branch.setName(branchName);
        branch.setProjectId(repository.getGitlabProjectId());

        Branch subBranch = new Branch();
        subBranch.setSourceName(branchName);
        subBranch.setProjectId(repository.getGitlabProjectId());

        if (!branchMapper.select(subBranch).isEmpty()) {
            throw new CommonException("error.branch.has.sub.branch");
        }
        agileFeignClient.deleteByBranchId(branchMapper.selectOne(branch).getId());
        try {
            gitlab4jclient
                    .getGitLabApi(userId)
                    .getRepositoryApi()
                    .deleteBranch(repository.getGitlabProjectId(), branchName);
        } catch (GitLabApiException e) {
            throw new CommonException("error.branch.delete");
        }

        if (branchMapper.delete(branch) != 1) {
            throw new CommonException("error.branch.delete");
        }
    }

    @Override
    public Branch queryBranchByName(Long projectId, String branchName) {
        //        try {
//            return gitlab4jclient.getGitLabApi()
//                    .getRepositoryApi()
//                    .getBranch(projectId, branchName);
//        } catch (GitLabApiException e) {
//            return new Branch();
//        }
        Repository repository = repositoryMapper.selectByPrimaryKey(projectId);
        Branch branch = new Branch();
        branch.setName(branchName);
        branch.setProjectId(repository.getGitlabProjectId());
        Branch res = branchMapper.selectOne(branch);
//        res.setIssueDTO(agileFeignClient.queryByBranchId(res.getId()).getBody());
        return res;
    }

    @Override
    public Branch queryBranchById(Long branchId) {
        //        try {
//            return gitlab4jclient.getGitLabApi()
//                    .getRepositoryApi()
//                    .getBranch(projectId, branchName);
//        } catch (GitLabApiException e) {
//            return new Branch();
//        }
        Branch res = branchMapper.selectByPrimaryKey(branchId);
//        res.setIssueDTO(agileFeignClient.queryByBranchId(res.getId()).getBody());
        return res;
    }

    @Override
    public List<Branch> listBranches(Long projectId, Integer userId) {
//        try {
//            return gitlab4jclient.getGitLabApi(userId)
//                    .getRepositoryApi()
//                    .getBranches(projectId);
//        } catch (GitLabApiException e) {
//            throw new CommonException("error.branch.get");
//        }
        Repository repository = repositoryMapper.selectByPrimaryKey(projectId);
        Branch branch = new Branch();
        branch.setProjectId(repository.getGitlabProjectId());
        List<Branch> res = branchMapper.select(branch);
//        res.forEach(v -> {
//            v.setIssueDTO(agileFeignClient.queryByBranchId(v.getId()).getBody());
//        });
        return res;
    }

}
