package per.zongwlee.oauth.infra.feign;

import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Member;
import org.gitlab4j.api.models.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import per.zongwlee.oauth.api.dto.RoleDTO;
import per.zongwlee.oauth.infra.feign.fallback.DevopsFeignClientFallback;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/16
 */
@FeignClient(value = "devops-gitlab-service",
        fallback = DevopsFeignClientFallback.class)
@Component
public interface DevopsFeignClient {

    /**
     * 创建用户
     *
     * @param password      密码
     * @param projectsLimit 创建项目上限
     * @param user          User
     * @return User
     */
    @PostMapping("/v1/users")
    ResponseEntity<User> create(@RequestParam(value = "password") String password,
                                @RequestParam(required = false, value = "projects_limit") Integer projectsLimit,
                                @RequestBody User user);

    /**
     * 根据用户Id获得用户信息
     *
     * @param username 用户名称
     * @return User
     */
    @GetMapping(value = "/v1/users/{username}/id")
    ResponseEntity<Integer> queryUserByUsername(@PathVariable(value = "username") String username);

    @PostMapping(value = "/v1/projects/members")
    ResponseEntity<Member> createMember(@RequestParam(value = "user_id") Integer userId);
}
