package per.zongwlee.gitlab.app.service;

import java.util.List;

import org.gitlab4j.api.models.Group;
import org.gitlab4j.api.models.Member;
import org.gitlab4j.api.models.Project;

import per.zongwlee.gitlab.api.dto.GroupDTO;
import per.zongwlee.gitlab.api.dto.MemberDto;

public interface GroupService {
    /**
     * 查询所有组
     *
     * @return List
     */
    List<Group> listGroups();


    /**
     * 创建组
     *
     * @param group    组对象
     * @param userId 用户名
     * @return Group
     */
    GroupDTO createGroup(GroupDTO group, Integer userId);


    /**
     * 更新组
     *
     * @param groupId  组对象Id
     * @param userId 用户名
     * @param group    组对象
     * @return Group
     */
    Group updateGroup(Integer groupId, Integer userId, Group group);

    /**
     * 删除组
     *
     * @param groupId 组对象Id
     */
    void deleteGroup(Integer groupId,Integer userId);

    /**
     * 查询组中的成员
     *
     * @param groupId 组对象Id
     * @return List
     */
    List<Member> listMember(Integer groupId);

    /**
     * 根据用户ID获得组成员信息
     *
     * @param groupId 组对象Id
     * @param userId  用户Id
     * @return Member
     */
    Member queryMemberByUserId(Integer groupId, Integer userId);

    /**
     * 增加组成员
     *
     * @param groupId 组对象Id
     * @param member  成员信息
     * @return Member
     */
    Member createMember(Integer groupId, MemberDto member);

    /**
     * 更新组成员
     *
     * @param groupId 组对象Id
     * @param member  成员信息
     * @return Member
     */
    Member updateMember(Integer groupId, MemberDto member);

    /**
     * 移除组成员
     *
     * @param groupId 组对象Id
     * @param userId  成员信息
     */
    void deleteMember(Integer groupId, Integer userId);

    /**
     * 获取项目列表
     *
     * @param groupId  组对象Id
     * @param userId 用户名
     * @return List
     */
    List<Project> listProjects(Integer groupId, Integer userId);

    /**
     * 根据组名查询组
     *
     * @param groupName 组名
     * @param  userId 用户Id
     * @return group
     */
    Group queryGroupByName(String groupName, Integer userId);

}
