package per.zongwlee.gitlab.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.GroupApi;
import org.gitlab4j.api.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.CommonException;
import per.zongwlee.gitlab.api.dto.GroupDTO;
import per.zongwlee.gitlab.api.dto.MemberDto;
import per.zongwlee.gitlab.app.service.GroupService;
import per.zongwlee.gitlab.infra.common.client.Gitlab4jClient;


@Service
public class GroupServiceImpl implements GroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);
    private Gitlab4jClient gitlab4jclient;

    public GroupServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Override
    public GroupDTO createGroup(GroupDTO group, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        try {
            GroupApi groupApi = gitLabApi.getGroupApi();
            groupApi.addGroup(group.getName(), group.getPath(), group.getDescription(),
                    null, null, group.getVisibility(), null,
                    group.getRequestAccessEnabled(), group.getParentId(), group.getSharedRunnersMinutesLimit());
            Group group1 = gitLabApi.getGroupApi().getGroup(group.getPath());
            GroupDTO groupDTO = new GroupDTO();
            BeanUtils.copyProperties(group1, groupDTO);
            return groupDTO;
        } catch (GitLabApiException e) {
            if (e.getHttpStatus() == 404) {
                try {
                    GroupDTO groupDTO = new GroupDTO();
                    Group group1 = gitLabApi.getGroupApi().getGroup(group.getPath());
                    BeanUtils.copyProperties(group1, groupDTO);
                    return groupDTO;
                } catch (GitLabApiException e1) {
                    LOGGER.info(e.getMessage());
                    throw new CommonException(e.getMessage(), e);
                }
            }
            throw new CommonException(e.getMessage(), e);
        }
    }

    @Override
    public Group updateGroup(Integer groupId, Integer userId, Group group) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        try {
            return gitLabApi.getGroupApi().updateGroup(groupId, group.getName(), group.getPath(),
                    group.getDescription(), null, null, group.getVisibility(), null,
                    group.getRequestAccessEnabled(), group.getParentId(), group.getSharedRunnersMinutesLimit());
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteGroup(Integer groupId, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        GroupApi groupApi = gitLabApi.getGroupApi();
        try {
            User user = gitLabApi.getUserApi().getUser(userId);
            List<Member> members = groupApi.getMembers(groupId)
                    .stream().filter(t -> user.getUsername().equals(t.getUsername())).collect(Collectors.toList());
            if (members != null && AccessLevel.OWNER.value.equals(members.get(0).getAccessLevel().value)) {
                groupApi.deleteGroup(groupId);
            } else {
                throw new CommonException("error.groups.deleteGroup.Owner");
            }
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage(), e);
        }
    }


    @Override
    public List<Project> listProjects(Integer groupId, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        try {
            return gitLabApi.getGroupApi().getProjects(groupId);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage(), e);
        }
    }

    @Override
    public Group queryGroupByName(String groupName, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        try {
            return gitLabApi.getGroupApi().getGroup(groupName);
        } catch (GitLabApiException e) {
            return null;
        }
    }

    @Override
    public List<Group> listGroups() {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        try {
            return gitLabApi.getGroupApi().getGroups();
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage(), e);
        }
    }

    @Override
    public List<Member> listMember(Integer groupId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        try {
            return gitLabApi.getGroupApi().getMembers(groupId);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage(), e);
        }
    }

    @Override
    public Member queryMemberByUserId(Integer groupId, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        try {
            return gitLabApi.getGroupApi().getMember(groupId, userId);
        } catch (GitLabApiException e) {
            return new Member();
        }
    }

    @Override
    public Member createMember(Integer groupId, MemberDto member) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        try {
            return gitLabApi.getGroupApi().addMember(groupId, member.getUserId(), member.getAccessLevel(),
                    member.getExpiresAt());
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage(), e);
        }
    }

    @Override
    public Member updateMember(Integer groupId, MemberDto member) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        try {
            return gitLabApi.getGroupApi().updateMember(groupId, member.getUserId(), member.getAccessLevel(),
                    member.getExpiresAt());
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteMember(Integer groupId, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        try {
            gitLabApi.getGroupApi().removeMember(groupId, userId);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage(), e);
        }
    }
}