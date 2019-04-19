package per.zongwlee.gitlab.api.controller.v1;

import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import per.zongwlee.gitlab.app.service.RepositoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1/{project_id}/tags")
public class TagsController {

    private RepositoryService repositoryService;

    public TagsController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    /**
     * 创建tag
     *
     * @param projectId 项目id
     * @param name      标签名
     * @param ref       标签源
     * @param userId    用户Id
     * @return Tag
     */
    @ApiOperation(value = "创建tag")
    @PostMapping
    public ResponseEntity<Tag> createTag(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "project_id") Integer projectId,
            @ApiParam(value = "标签名", required = true)
            @RequestParam("name") String name,
            @ApiParam(value = "标签源", required = true)
            @RequestParam("ref") String ref,
            @ApiParam(value = "标签描述")
            @RequestParam(value = "message", required = false, defaultValue = "") String msg,
            @ApiParam(value = "发布日志")
            @RequestBody(required = false) String releaseNotes,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "user_id", required = false) Integer userId) {
        return Optional.ofNullable(repositoryService.createTag(projectId, name, ref, msg, releaseNotes, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.tag.create"));
    }

    /**
     * 更新 tag
     *
     * @param projectId    项目id
     * @param name         标签名
     * @param releaseNotes 发布日志
     * @return Tag
     */
    @ApiOperation(value = "更新tag")
    @PutMapping
    public ResponseEntity<Tag> updateTagRelease(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "project_id") Integer projectId,
            @ApiParam(value = "标签名", required = true)
            @RequestParam("name") String name,
            @ApiParam(value = "发布日志")
            @RequestBody(required = false) String releaseNotes,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "user_id", required = false) Integer userId) {
        return Optional.ofNullable(repositoryService.updateTagRelease(projectId, name, releaseNotes, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.tag.create"));
    }

    /**
     * 根据标签名删除标签
     *
     * @param projectId 项目id
     * @param name      标签名
     * @param userId    用户Id
     */
    @ApiOperation(value = "删除tag")
    @DeleteMapping
    public ResponseEntity deleteTag(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "project_id") Integer projectId,
            @ApiParam(value = "标签名", required = true)
            @RequestParam("name") String name,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "user_id", required = false)
                    Integer userId) {
        repositoryService.deleteTag(projectId, name, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 获取tag列表
     *
     * @param projectId 项目id
     * @param userId    用户Id
     * @return List
     */
    @ApiOperation(value = "获取tag列表")
    @GetMapping
    public ResponseEntity<List<Tag>> listTags(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "project_id") Integer projectId,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "user_id", required = false) Integer userId) {
        return Optional.ofNullable(repositoryService.listTags(projectId, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.tag.get"));
    }

    /**
     * 分页获取tag列表
     *
     * @param projectId   项目id
     * @param pageRequest 分页参数
     * @param userId      用户Id
     * @return List
     */
    @ApiOperation(value = "分页获取tag列表")
    @GetMapping(value = "/page")
    public ResponseEntity<List<Tag>> listTagsByPage(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "project_id") Integer projectId,
            @ApiParam(value = "分页参数") PageRequest pageRequest,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "user_id", required = false) Integer userId) {
        return Optional.ofNullable(repositoryService.listTagsByPage(projectId, pageRequest, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.tag.getPage"));
    }

}
