package per.zongwlee.oauth.api.service;


import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import per.zongwlee.oauth.api.dto.AccessToken;
import per.zongwlee.oauth.api.dto.ReturnRoleDTO;
import per.zongwlee.oauth.api.dto.RoleDTO;
import per.zongwlee.oauth.domain.entity.RoleE;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/03/25
 */
public interface RoleService {
    AccessToken login(RoleDTO roleDTO);

    String register(RoleDTO roleDTO);

    ReturnRoleDTO updateSelective(RoleDTO roleDTO);

    boolean checkEmail(String email);

    ReturnRoleDTO queryById(Long roleId);

    Page<ReturnRoleDTO> pageQuery(PageRequest pageRequest);

    boolean checkAuthorazition(String jwtToken);
}
