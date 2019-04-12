package io.choerodon.issue.api.controller;

import io.choerodon.core.exception.CommonException;
import io.choerodon.issue.api.dto.PriorityDTO;
import io.choerodon.issue.api.service.PriorityService;
import io.choerodon.issue.api.validator.PriorityValidator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author cong.cheng
 * @date 2018/8/21
 */
@RestController
@RequestMapping(value = "/v1")
public class PriorityController {
    private static final String YES = "1";
    @Autowired
    private PriorityService priorityService;

    @Autowired
    private PriorityValidator priorityValidator;


    @PostMapping("/test")
    public ResponseEntity<PriorityDTO> create2(@RequestBody PriorityDTO priorityDTO) {
        priorityValidator.createValidate(priorityDTO);
        return new ResponseEntity<>(priorityService.create(1L, priorityDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "创建优先级")
    @PostMapping("/organizations/{organization_id}/priority")
    public ResponseEntity<PriorityDTO> create(@PathVariable("organization_id") Long organizationId, @RequestBody PriorityDTO priorityDTO) {
        priorityValidator.createValidate(priorityDTO);
        return new ResponseEntity<>(priorityService.create(organizationId, priorityDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "删除优先级")
    @DeleteMapping(value = "/{priority_id}")
    public ResponseEntity<Boolean> delete(@PathVariable("organization_id") Long organizationId, @PathVariable("priority_id") Long priorityId) {
        return new ResponseEntity<>(priorityService.delete(organizationId, priorityId), HttpStatus.OK);
    }

    @ApiOperation(value = "更新优先级")
    @PutMapping(value = "/organizations/{organization_id}/priority/{priority_id}")
    public ResponseEntity<PriorityDTO> update(@PathVariable("organization_id") Long organizationId, @PathVariable("priority_id") Long priorityId,
                                              @RequestBody @Valid PriorityDTO priorityDTO) {
        priorityDTO.setId(priorityId);
        priorityDTO.setOrganizationId(organizationId);
        priorityValidator.updateValidate(priorityDTO);
        return new ResponseEntity<>(priorityService.update(priorityDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "校验优先级名字是否未被使用")
    @GetMapping(value = "/organizations/{organization_id}/priority/check_name")
    public ResponseEntity<Boolean> checkName(@PathVariable("organization_id") Long organizationId, @RequestParam(value = "priority_id", required = false) Long priorityId, @RequestParam("name") String name) {
        return new ResponseEntity<>(priorityService.checkName(organizationId, priorityId, name), HttpStatus.OK);
    }

    @ApiOperation(value = "更新展示顺序")
    @PutMapping(value = "/organizations/{organization_id}/priority/sequence")
    public ResponseEntity<List<PriorityDTO>> updateByList(@PathVariable("organization_id") Long organizationId,
                                                          @RequestBody List<PriorityDTO> list) {

        return new ResponseEntity<>(priorityService.updateByList(list,organizationId), HttpStatus.OK);
    }

//    @Permission(level = ResourceLevel.ORGANIZATION)
//    @ApiOperation(value = "优先级下拉列表（用于创建问题）")
//    @GetMapping(value = "/organizations/{organization_id}/priority/list")
//    public ResponseEntity<Map<String,Object>> selectByOrgId(@PathVariable("organization_id") Long organizationId) {
//        Map<String,Object> map = new HashMap<>(2);
//        PriorityDTO priorityDTO = new PriorityDTO();
//        priorityDTO.setOrganizationId(organizationId);
//        List<PriorityDTO> priorityDTOS = priorityService.selectAll(priorityDTO,null);
//        map.put("list",priorityDTOS);
//        map.put("defaultId", priorityDTOS.stream().filter(x->x.getIsDefault().equals(YES)).map(PriorityDTO::getId).findFirst().orElse(null));
//        return new ResponseEntity<>(map, HttpStatus.OK);
//    }

    @ApiOperation(value = "根据组织id查询优先级,map")
    @GetMapping("/organizations/{organization_id}/priority/list")
    public ResponseEntity<Map<Long, PriorityDTO>> queryByOrganizationId(@ApiParam(value = "组织id", required = true)
                                                                @PathVariable("organization_id") Long organizationId) {
        return Optional.ofNullable(priorityService.queryByOrganizationId(organizationId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.priorityList.get"));

    }

    @ApiOperation(value = "根据组织id查询默认优先级")
    @GetMapping("/organizations/{organization_id}/priority/default")
    public ResponseEntity<PriorityDTO> queryDefaultByOrganizationId(@ApiParam(value = "组织id", required = true)
                                                                    @PathVariable("organization_id") Long organizationId) {
        return Optional.ofNullable(priorityService.queryDefaultByOrganizationId(organizationId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.priority.get"));

    }

    @ApiOperation(value = "根据组织id查询优先级,list")
    @GetMapping("/organizations/{organization_id}/priority/list_by_org")
    public ResponseEntity<List<PriorityDTO>> queryByOrganizationIdList(@ApiParam(value = "组织id", required = true)
                                                                        @PathVariable("organization_id") Long organizationId) {
        return Optional.ofNullable(priorityService.queryByOrganizationIdList(organizationId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.priorityList.get"));
    }

    @ApiOperation(value = "根据id查询优先级")
    @GetMapping("/organizations/{organization_id}/priority/{id}")
    public ResponseEntity<PriorityDTO> queryById(@ApiParam(value = "组织id", required = true)
                                                       @PathVariable("organization_id") Long organizationId,
                                                       @ApiParam(value = "id", required = true)
                                                       @PathVariable Long id) {
        return Optional.ofNullable(priorityService.queryById(organizationId, id))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.priority.get"));

    }


}
