package per.zongwlee.oauth.domain.entity;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import per.zongwlee.oauth.infra.enums.RoleType;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/03/25
 */
@ModifyAudit
@VersionAudit
@Table(name = "role")
public class RoleE extends AuditDomain {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private Integer type;
    private String password;
    private Integer gitlabId;

    private Long objectVersionNumber;

    @Override
    public Long getObjectVersionNumber() {
        return objectVersionNumber;
    }

    @Override
    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGitlabId() {
        return gitlabId;
    }

    public void setGitlabId(Integer gitlabId) {
        this.gitlabId = gitlabId;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getType() {
        return type;
    }

    public String getStringType() {
        String sType = null;
        switch (type) {
            case 0:
                sType = RoleType.ORDINARY.toString();
                break;
            case 1:
                sType = RoleType.MANAGER.toString();
                break;
            case 2:
                sType = RoleType.ROOT.toString();
                break;
            case 3:
                sType = RoleType.SUPERMANAGER.toString();
                break;
            case 4:
                sType = RoleType.PROBATIONER.toString();
                break;
            default:
                break;
        }
        return sType;
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
}