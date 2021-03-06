package per.zongwlee.oauth.api.controller.v1;

import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import per.zongwlee.oauth.api.dto.AccessToken;
import per.zongwlee.oauth.api.dto.ReturnRoleDTO;
import per.zongwlee.oauth.api.dto.RoleDTO;
import per.zongwlee.oauth.api.service.RoleService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


/**
 * @author zongw.lee@gmail.com
 * @since 2019/03/25
 */
@RestController
@RequestMapping("/v1")
public class OauthController {

    @Autowired
    RoleService roleService;

    @PostMapping(value = "/login")
    public ResponseEntity<AccessToken> login(@RequestBody RoleDTO roleDTO) {
        return Optional.ofNullable(roleService.login(roleDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.user.login"));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ReturnRoleDTO> register(@RequestBody RoleDTO roleDTO) {
        return Optional.ofNullable(roleService.register(roleDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.user.register"));
    }

    @GetMapping(value = "/check/authorazition")
    public Boolean checkAuthorazition(@RequestParam("jwtToken") String jwtToken) {
        return roleService.checkAuthorazition(jwtToken);
    }

    @GetMapping(value = "/user/detail")
    public ResponseEntity<ReturnRoleDTO> getUserByAuthorazition(HttpServletRequest request) {
        return Optional.ofNullable(roleService.getUserByAuthorazition(request.getHeader("Jwt_Token")))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.user.get.detail"));
    }

    @GetMapping(value = "/{role_id}")
    public ResponseEntity<ReturnRoleDTO> queryById(@PathVariable("role_id") Long roleId) {
        return Optional.ofNullable(roleService.queryById(roleId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.user.query"));
    }

    @GetMapping(value = "/list/all")
    public ResponseEntity<Page<ReturnRoleDTO>> query(@ApiParam(value = "分页参数") PageRequest pageRequest) {
        return Optional.ofNullable(roleService.pageQuery(pageRequest))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.user.query"));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<ReturnRoleDTO> updateSelective(@RequestBody RoleDTO roleDTO) {
        return Optional.ofNullable(roleService.updateSelective(roleDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.user.update"));
    }

    @PutMapping(value = "/update/password/{role_id}")
    public ResponseEntity<Boolean> updatePasswordById(@PathVariable(value = "role_id") Long id,
                                                      @RequestParam(value = "password") String password) {
        roleService.updatePasswordById(id, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/check/email")
    public Boolean query(@RequestParam String email) {
        return roleService.checkEmail(email);
    }
}