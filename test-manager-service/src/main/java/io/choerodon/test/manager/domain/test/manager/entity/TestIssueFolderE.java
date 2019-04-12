package io.choerodon.test.manager.domain.test.manager.entity;

import io.choerodon.test.manager.domain.repository.TestIssueFolderRepository;
import io.choerodon.test.manager.infra.exception.IssueFolderException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zongw.lee@gmail.com on 08/30/2018
 */
@Component
@Scope("prototype")
public class TestIssueFolderE {

    public static final String TYPE_CYCLE="cycle";
    public static final String TYPE_TEMP="temp";

    private Long folderId;

    private String name;

    private Long versionId;

    private Long projectId;

    private String type;

    private Long objectVersionNumber;

    private Boolean newFolder;

    @Autowired
    private TestIssueFolderRepository testIssueFolderRepository;

    public TestIssueFolderE queryOne(TestIssueFolderE testIssueFolderE) {
        return testIssueFolderRepository.queryOne(testIssueFolderE);
    }

    public TestIssueFolderE queryByPrimaryKey(){
        return testIssueFolderRepository.queryByPrimaryKey(this.folderId);
    }

    public List<TestIssueFolderE> queryAllUnderProject() {
        return testIssueFolderRepository.queryAllUnderProject(this);
    }

    public TestIssueFolderE addSelf() {
        return testIssueFolderRepository.insert(this);
    }

    public TestIssueFolderE updateSelf() {
        return testIssueFolderRepository.update(this);
    }

    public void deleteSelf() {
        testIssueFolderRepository.delete(this);
    }


    public Long getObjectVersionNumber() {
        return objectVersionNumber;
    }

    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

   public TestIssueFolderE validateType(){
        if(!(StringUtils.equals(type,TYPE_CYCLE)|| StringUtils.equals(type,TYPE_TEMP))){
            throw new IssueFolderException(IssueFolderException.ERROR_FOLDER_TYPE);
        }
        return this;
    }

    public Boolean getNewFolder() {
        return newFolder;
    }

    public void setNewFolder(Boolean newFolder) {
        this.newFolder = newFolder;
    }
}
