package per.zongwlee.oauth.domain.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/03/25
 */
public class JwtRole implements UserDetails {

    private Long id;
    private String userName;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public JwtRole() {
    }

    // 写一个能直接使用user创建jwtUser的构造器
    public JwtRole(RoleE user) {
        id = user.getId();
        userName = user.getName();
        password = user.getPassword();
        authorities = Collections.singleton(new SimpleGrantedAuthority(user.getStringType()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "JwtRole{" +
                "id=" + id +
                ", username='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }

}
