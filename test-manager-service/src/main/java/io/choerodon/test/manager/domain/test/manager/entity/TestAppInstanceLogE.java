package io.choerodon.test.manager.domain.test.manager.entity;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name ="test_app_instance_log")
@ModifyAudit
@VersionAudit
public class TestAppInstanceLogE extends AuditDomain {

    @Id
    @GeneratedValue
    private Long id;

    private String log;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
