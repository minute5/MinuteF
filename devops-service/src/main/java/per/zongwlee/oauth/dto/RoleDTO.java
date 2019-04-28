package per.zongwlee.oauth.dto;

import per.zongwlee.oauth.dto.enums.RoleType;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/03/25
 */
public class RoleDTO {
    private Long id;
    private String name;
    private String email;
    private Long mobile;
    private Integer type;
    private String password;
    private Boolean isRememberMe;
    private String roleType;

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public Boolean getIsRememberMe() {
        return isRememberMe;
    }

    public void setIsRememberMe(Boolean isRememberMe) {
        this.isRememberMe = isRememberMe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void loadRoleType() {
        switch (type) {
            case 0:
                roleType = RoleType.ORDINARY.toString();
                break;
            case 1:
                roleType = RoleType.MANAGER.toString();
                break;
            case 2:
                roleType = RoleType.ROOT.toString();
                break;
            case 3:
                roleType = RoleType.SUPERMANAGER.toString();
                break;
            case 4:
                roleType = RoleType.PROBATIONER.toString();
                break;
            default:
                break;
        }
    }
}
