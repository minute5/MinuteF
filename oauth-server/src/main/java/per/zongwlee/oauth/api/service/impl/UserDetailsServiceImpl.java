package per.zongwlee.oauth.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import per.zongwlee.oauth.domain.entity.JwtRole;
import per.zongwlee.oauth.domain.entity.RoleE;
import per.zongwlee.oauth.infra.mapper.RoleMapper;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/03/25
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        RoleE roleE = new RoleE();
        roleE.setName(name);
        RoleE user = roleMapper.selectOne(roleE);
        return new JwtRole(user);
    }

}
