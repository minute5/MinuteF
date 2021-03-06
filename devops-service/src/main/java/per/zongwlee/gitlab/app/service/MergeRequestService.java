package per.zongwlee.gitlab.app.service;

import java.util.List;

import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.MergeRequest;


public interface MergeRequestService {

    /**
     * 创建merge请求
     *
     * @param projectId    工程ID
     * @param sourceBranch 源分支
     * @param targetBranch 目标分支
     * @param title        标题
     * @param description  描述
     * @param userId     用户Id Optional
     * @return MergeRequest
     */
    MergeRequest createMergeRequest(Long projectId, String sourceBranch,
                                    String targetBranch, String title,
                                    String description, Integer userId);


    /**
     * 获取合并请求merge request
     *
     * @param projectId      项目id
     * @param mergeRequestId 合并请求id
     * @param userId       用户Id Optional
     * @return MergeRequest
     */
    MergeRequest queryMergeRequest(Long projectId, Integer mergeRequestId, Integer userId);

    /**
     * 获取合并请求列表merge request
     *
     * @param projectId 项目id
     * @return MergeRequest
     */
    List<MergeRequest> listMergeRequests(Long projectId);


    /**
     * 执行merge请求
     *
     * @param projectId                 项目id
     * @param mergeRequestId            merge请求的id
     * @param mergeCommitMessage        merge的commit信息
     * @param shouldRemoveSourceBranch  merge后是否删除该分支
     * @param mergeWhenPipelineSucceeds pipeline成功后自动合并分支
     * @param userId                  用户Id
     * @return MergeRequest
     */
    MergeRequest acceptMergeRequest(
            Long projectId, Integer mergeRequestId, String mergeCommitMessage,
            Boolean shouldRemoveSourceBranch, Boolean mergeWhenPipelineSucceeds,
            Integer userId);

    /**
     * 查询合并请求的commits
     *
     * @param projectId      项目id
     * @param mergeRequestId 合并请求ID
     * @param userId 用户Id
     * @return List
     */
    List<Commit> listCommits(Long projectId, Integer mergeRequestId, Integer userId);

    /**
     * 删除合并请求
     *
     * @param projectId      项目id
     * @param mergeRequestId 合并请求ID
     */
    void deleteMergeRequest(Long projectId, Integer mergeRequestId);

}

