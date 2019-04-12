package per.zongwlee.oauth.api.controller.v1;

import io.choerodon.core.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import per.zongwlee.oauth.api.dto.AccessToken;
import per.zongwlee.oauth.api.dto.RoleDTO;
import per.zongwlee.oauth.api.service.RoleService;
import per.zongwlee.oauth.domain.entity.RoleE;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
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
                .orElseThrow(() -> new CommonException("error.login"));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody RoleDTO roleDTO) {
        return Optional.ofNullable(roleService.register(roleDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.register"));
    }

    @GetMapping(value = "/check/authorazition")
    public Boolean checkAuthorazition(@RequestParam("jwtToken") String jwtToken) {
        return roleService.checkAuthorazition(jwtToken);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<RoleE> updateSelective(@RequestBody RoleDTO roleDTO) {
        return Optional.ofNullable(roleService.updateSelective(roleDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.update"));
    }

    @GetMapping(value = "/check/email")
    public Boolean query(@RequestParam String email) {
        return roleService.checkEmail(email);
    }
}