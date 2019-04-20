package per.zongwlee.gitlab.app.service.impl;

import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.zongwlee.gitlab.app.service.RepositoryService;
import per.zongwlee.gitlab.domain.entity.Branch;
import per.zongwlee.gitlab.infra.common.client.Gitlab4jClient;
import per.zongwlee.gitlab.infra.mapper.BranchMapper;

import java.util.List;


@Service
public class RepositoryServiceImpl implements RepositoryService {

    private Gitlab4jClient gitlab4jclient;

    public RepositoryServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Autowired
    BranchMapper branchMapper;

    @Override
    public Branch createBranch(Integer projectId, String branchName, String source, Integer userId, Long issueId) {
        try {
            this.gitlab4jclient.getGitLabApi(userId).getRepositoryApi()
                    .createBranch(projectId, branchName, source);
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
        branch.setCreateorId(userId);
        branch.setActive(0);
        branch.setIssueId(issueId);
        branch.setProjectId(projectId);
        if (branchMapper.insert(branch) != 1) {
            throw new CommonException("error.branch.insert");
        }
        return branchMapper.selectOne(branch);
    }

    @Override
    public List<Tag> listTags(Integer projectId, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId).getRepositoryApi().getTags(projectId);
        } catch (GitLabApiException e) {
            throw new CommonException("error.tag.get");
        }
    }

    @Override
    public List<Tag> listTagsByPage(Integer projectId, PageRequest pageRequest, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getRepositoryApi()
                    .getTags(projectId, pageRequest.getPage(), pageRequest.getSize());
        } catch (GitLabApiException e) {
            throw new CommonException("error.tag.getPage");
        }
    }

    @Override
    public Tag createTag(Integer projectId, String tagName, String ref, String msg, String releaseNotes, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getRepositoryApi()
                    .createTag(projectId, tagName, ref, msg, releaseNotes);
        } catch (GitLabApiException e) {
            throw new CommonException("error.tag.create: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteTag(Integer projectId, String tagName, Integer userId) {
        try {
            gitlab4jclient
                    .getGitLabApi(userId)
                    .getRepositoryApi()
                    .deleteTag(projectId, tagName);
        } catch (GitLabApiException e) {
            throw new CommonException("error.tag.delete: " + e.getMessage(), e);
        }
    }

    @Override
    public Tag updateTagRelease(Integer projectId, String name, String releaseNotes, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getRepositoryApi()
                    .updateTagRelease(projectId, name, releaseNotes);
        } catch (GitLabApiException e) {
            throw new CommonException("error.tag.update: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteBranch(Integer projectId, String branchName, Integer userId) {
        Branch branch = new Branch();
        branch.setName(branchName);
        branch.setProjectId(projectId);

        Branch subBranch = new Branch();
        subBranch.setSourceName(branchName);
        subBranch.setProjectId(projectId);

        if (!branchMapper.select(subBranch).isEmpty()) {
            throw new CommonException("error.branch.has.sub.branch");
        }
        if (branchMapper.delete(branch) != 1) {
            throw new CommonException("error.branch.delete");
        }

        try {
            gitlab4jclient
                    .getGitLabApi(userId)
                    .getRepositoryApi()
                    .deleteBranch(projectId, branchName);
        } catch (GitLabApiException e) {
            throw new CommonException("error.branch.delete");
        }
    }

    @Override
    public Branch queryBranchByName(Integer projectId, String branchName) {
        //        try {
//            return gitlab4jclient.getGitLabApi()
//                    .getRepositoryApi()
//                    .getBranch(projectId, branchName);
//        } catch (GitLabApiException e) {
//            return new Branch();
//        }
        Branch branch = new Branch();
        branch.setName(branchName);
        branch.setProjectId(projectId);
        return branchMapper.selectOne(branch);
    }

    @Override
    public List<Branch> listBranches(Integer projectId, Integer userId) {
//        try {
//            return gitlab4jclient.getGitLabApi(userId)
//                    .getRepositoryApi()
//                    .getBranches(projectId);
//        } catch (GitLabApiException e) {
//            throw new CommonException("error.branch.get");
//        }
        Branch branch = new Branch();
        branch.setProjectId(projectId);
        return branchMapper.select(branch);
    }

}
