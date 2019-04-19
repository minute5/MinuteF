package per.zongwlee.gitlab.app.service;

import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.gitlab4j.api.models.Tag;
import per.zongwlee.gitlab.domain.entity.Branch;

import java.util.List;


public interface RepositoryService {

    /**
     * 创建新分支
     *
     * @param projectId  项目id
     * @param branchName 分支名
     * @param source     源分支名
     * @param userId     用户Id
     * @return Branch
     */
    Branch createBranch(Integer projectId, String branchName, String source, Integer userId, Long issueId);

    /**
     * 获取tag列表
     *
     * @param projectId 项目id
     * @param userId    用户Id
     * @return List
     */
    List<Tag> listTags(Integer projectId, Integer userId);

    /**
     * 分页获取tag列表
     *
     * @param projectId 项目id
     * @param pageRequest 分页参数
     * @param userId    用户Id
     * @return List
     */
    List<Tag> listTagsByPage(Integer projectId, PageRequest pageRequest, Integer userId);

    /**
     * 创建tag
     *
     * @param projectId    项目id
     * @param tagName      标签名
     * @param ref          标签源
     * @param userId       用户Id
     * @param msg          描述
     * @param releaseNotes 发布日志
     * @return Tag
     */
    Tag createTag(Integer projectId, String tagName, String ref, String msg, String releaseNotes, Integer userId);

    /**
     * 根据标签名删除tag
     *
     * @param projectId 项目id
     * @param tagName   标签名
     * @param userId    用户Id
     */
    void deleteTag(Integer projectId, String tagName, Integer userId);

    /**
     * 更新 tag
     *
     * @param projectId    项目id
     * @param name         标签名
     * @param releaseNotes 发布日志
     * @return Tag
     */
    Tag updateTagRelease(Integer projectId, String name, String releaseNotes, Integer userId);

    /**
     * 根据分支名删除分支
     *
     * @param projectId  项目id
     * @param branchName 分支名
     * @param userId     用户Id
     */
    void deleteBranch(Integer projectId, String branchName, Integer userId);

    /**
     * 根据分支名查询分支
     *
     * @param projectId  项目id
     * @param branchName 分支名
     * @return Branch
     */
    Branch queryBranchByName(Integer projectId, String branchName);


    /**
     * 获取项目下所有分支
     *
     * @param projectId 项目id
     * @param userId    用户Id
     * @return List
     */
    List<Branch> listBranches(Integer projectId, Integer userId);


}
