package per.zongwlee.gitlab.app.service;

import org.gitlab4j.api.models.DeployKey;
import org.gitlab4j.api.models.Member;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.Variable;
import per.zongwlee.gitlab.api.dto.MemberDto;
import per.zongwlee.gitlab.domain.entity.Repository;

import java.util.List;
import java.util.Map;


public interface ProjectService {

    /**
     * 通过项目名称创建项目
     *
     * @param projectName 项目名
     * @param userId      用户Id
     * @return Project
     */
    Repository createProject(String projectName, String gitlabProjectName, Integer userId);

    /**
     * 删除项目
     *
     * @param projectId 项目 id
     */
    void deleteProject(Long projectId);


    /**
     * 通过group名和项目名删除项目
     *
     * @param groupName   项目 id
     * @param userId      用户名
     * @param projectName 项目名
     */
    void deleteProjectByName(String groupName, String projectName, Integer userId);

    /**
     * 更新项目
     *
     * @param repository 项目
     * @param userId     用户Id
     * @return Project
     */
    Repository updateProject(Repository repository, Integer userId);

    /**
     * 通过项目id查询项目
     *
     * @param projectId 项目id
     * @return Project
     */
    Repository getProjectById(Long projectId);

    /**
     * 查询所有项目
     *
     * @return Repository
     */
    List<Repository> getProject();

    /**
     * 添加项目成员
     *
     */
    Member createMember( Integer userId);

    /**
     * 移除项目成员
     *
     * @param userId    用户id
     */
    void deleteMember(Integer userId);

    /**
     * 获取项目下所有成员
     *
     * @return List
     */
    List<Member> getAllMemberByProjectId();

}
