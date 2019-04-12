package per.zongwlee.oauth.api.dto;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/03/25
 */
public class AccessToken {
    private String accessToken;
    private String tokenType;
    private long expiresIn;

    public AccessToken(String accessToken, long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public String getAccess_token() {
        return accessToken;
    }

    public void setAccess_token(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getToken_type() {
        return tokenType;
    }

    public void setToken_type(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpires_in() {
        return expiresIn;
    }

    public void setExpires_in(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}