package per.zongwlee.oauth.infra.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/03/25
 */
public class CustomAuthenticationException extends AuthenticationException {
    private final transient Object[] parameters;

    public CustomAuthenticationException(String msg, Throwable t, Object... parameters) {
        super(msg, t);
        this.parameters = parameters;
    }

    public CustomAuthenticationException(String msg, Object... parameters) {
        super(msg);
        this.parameters = parameters;
    }

    public Object[] getParameters() {
        return parameters;
    }

}
