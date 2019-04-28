package per.zongwlee.gitlab.domain.entity;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import per.zongwlee.agile.dto.IssueDTO;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/15
 */
@ModifyAudit
@VersionAudit
@Table(name = "gitlab_branch")
public class Branch extends AuditDomain {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String sourceName;
    private Integer creatorId;
    private Integer active;
    private Integer projectId;

    @Transient
    private IssueDTO issueDTO;

    private Long objectVersionNumber;

    public IssueDTO getIssueDTO() {
        return issueDTO;
    }

    public void setIssueDTO(IssueDTO issueDTO) {
        this.issueDTO = issueDTO;
    }

    @Override
    public Long getObjectVersionNumber() {
        return objectVersionNumber;
    }

    @Override
    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}
