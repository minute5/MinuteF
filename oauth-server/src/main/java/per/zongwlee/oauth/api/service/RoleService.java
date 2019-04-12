package per.zongwlee.oauth.api.service;


import per.zongwlee.oauth.api.dto.AccessToken;
import per.zongwlee.oauth.api.dto.RoleDTO;
import per.zongwlee.oauth.domain.entity.RoleE;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/03/25
 */
public interface RoleService {
    AccessToken login(RoleDTO roleDTO);

    String register(RoleDTO roleDTO);

    RoleE updateSelective(RoleDTO roleDTO);

    boolean checkEmail(String email);


    boolean checkAuthorazition(String jwtToken);
}
