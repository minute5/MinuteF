package per.zongwlee.gitlab.domain.entity;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/15
 */
@ModifyAudit
@VersionAudit
@Table(name = "gitlab_repository")
public class Repository extends AuditDomain {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String gitlabName;
    private String url;
    private Integer gitlabId;
    private Integer gitlabGroupId;
    private Integer gitlabProjectId;

    private Long objectVersionNumber;

    @Override
    public Long getObjectVersionNumber() {
        return objectVersionNumber;
    }

    @Override
    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }

    public Integer getGitlabGroupId() {
        return gitlabGroupId;
    }

    public void setGitlabGroupId(Integer gitlabGroupId) {
        this.gitlabGroupId = gitlabGroupId;
    }

    public Integer getGitlabProjectId() {
        return gitlabProjectId;
    }

    public void setGitlabProjectId(Integer gitlabProjectId) {
        this.gitlabProjectId = gitlabProjectId;
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

    public String getGitlabName() {
        return gitlabName;
    }

    public void setGitlabName(String gitlabName) {
        this.gitlabName = gitlabName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getGitlabId() {
        return gitlabId;
    }

    public void setGitlabId(Integer gitlabId) {
        this.gitlabId = gitlabId;
    }
}
