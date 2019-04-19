package per.zongwlee.gitlab.api.controller.v1;

import io.choerodon.core.exception.CommonException;
import io.choerodon.core.exception.CommonException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import per.zongwlee.gitlab.app.service.ProjectService;
import per.zongwlee.gitlab.domain.entity.Repository;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/v1/projects")
public class ProjectsController {

    private static final String GROUPNAME = "minuteF";

    private ProjectService projectService;

    public ProjectsController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * 通过项目名称创建项目
     *
     * @param projectName 项目名
     * @param userId      用户Id
     * @return Project
     */
    @ApiOperation(value = "通过项目名称创建项目")
    @PostMapping
    public ResponseEntity<Repository> create(
            @ApiParam(value = "项目名称", required = true)
            @RequestParam("project_name") String projectName,
            @ApiParam(value = "gitlab项目名称", required = true)
            @RequestParam("gitlab_project_name") String gitlabProjectName,
            @ApiParam(value = "用户Id")
            @RequestParam(required = false, value = ("user_id")) Integer userId) {
        return Optional.ofNullable(projectService.createProject(projectName, gitlabProjectName, userId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.projects.create.name"));
    }

    /**
     * 删除项目
     *
     * @param projectId 项目 id
     */
    @ApiOperation(value = " 删除项目")
    @DeleteMapping(value = "/{project_id}")
    public ResponseEntity delete(
            @ApiParam(value = "项目ID", required = true)
            @PathVariable(value = "project_id") Integer projectId) {
        projectService.deleteProject(projectId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 通过group name project name删除项目
     *
     * @param groupName   组名
     * @param projectName 项目名
     * @param userId      用户Id
     */
    @ApiOperation(value = " 删除项目")
    @DeleteMapping(value = "/{groupName}/{projectName}")
    public ResponseEntity delete(
            @ApiParam(value = "gitlab group name", required = true)
            @PathVariable String groupName,
            @ApiParam(value = "项目名", required = true)
            @PathVariable String projectName,
            @ApiParam(value = "用户Id称")
            @RequestParam(required = false) Integer userId) {
        projectService.deleteProjectByName(groupName, projectName, userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 更新项目
     *
     * @param repository 项目
     * @param userId     用户Id
     * @return Project
     */
    @ApiOperation(value = "更新项目")
    @PutMapping
    public ResponseEntity<Repository> update(
            @ApiParam(value = "用户Id", required = true)
            @RequestParam(value = "user_id") Integer userId,
            @ApiParam(value = "项目信息", required = true)
            @PathVariable Repository repository) {
        return Optional.ofNullable(projectService.updateProject(repository, userId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.projects.update"));
    }

    /**
     * 通过项目id查询项目
     *
     * @param projectId 项目id
     * @return Project
     */
    @ApiOperation(value = "通过项目id查询项目")
    @GetMapping(value = "/{project_id}")
    public ResponseEntity<Repository> queryProjectById(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "project_id") Long projectId) {
        return Optional.ofNullable(projectService.getProjectById(projectId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.project.get"));
    }

//    /**
//     * 通过组名项目名查询项目
//     *
//     * @param userId      项目Id
//     * @param projectName 项目名
//     * @return Project
//     */
//    @ApiOperation(value = "通过项目名查询项目")
//    @GetMapping(value = "/queryByName")
//    public ResponseEntity<Repository> queryByName(
//            @ApiParam(value = "用户", required = true)
//            @RequestParam(value = "user_id") Integer userId,
//            @ApiParam(value = "项目名", required = true)
//            @RequestParam(value = "project_id") String projectName) {
//        return Optional.ofNullable(projectService.getProject(userId, GROUPNAME, projectName))
//                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
//                .orElseThrow(() -> new CommonException("error.project.get"));
//    }

    /**
     * 添加项目成员
     */
    @ApiOperation(value = "添加项目成员")
    @PostMapping(value = "/members")
    public ResponseEntity<Member> createMember(
            @ApiParam(value = "用户Id", required = true)
            @RequestParam(value = "user_id") Integer userId) {
        return Optional.ofNullable(projectService.createMember(userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.groups.member.create"));
    }

    /**
     * 移除项目成员
     *
     * @param userId 用户id
     */
    @ApiOperation(value = "移除项目成员")
    @DeleteMapping(value = "/members")
    public ResponseEntity deleteMember(
            @ApiParam(value = "用户ID", required = true)
            @PathVariable(value = "user_id") Integer userId) {
        projectService.deleteMember(userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 获取项目下所有成员
     *
     * @return List
     */
    @ApiOperation(value = "获取项目下所有成员")
    @GetMapping(value = "/members/list")
    public ResponseEntity<List<Member>> getAllMemberByProjectId() {
        return Optional.ofNullable(projectService.getAllMemberByProjectId())
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.project.member.list"));
    }
}
