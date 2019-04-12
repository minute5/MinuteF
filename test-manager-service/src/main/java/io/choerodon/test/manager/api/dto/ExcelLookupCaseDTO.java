package io.choerodon.test.manager.api.dto;

import io.choerodon.agile.api.dto.*;

import java.util.List;

/**
 * Created by zongw.lee@gmail.com on 18/10/2018
 */
public class ExcelLookupCaseDTO {
    //优先级
    private List<LookupValueDTO> lookupValueDTOS;

    private List<UserDTO> userDTOS;

    private List<ProductVersionDTO> productVersionDTOS;

    private List<TestIssueFolderDTO> testIssueFolderDTOS;

    private List<IssueStatusDTO> issueStatusDTOS;

    public ExcelLookupCaseDTO(List<LookupValueDTO> lookupValueDTOS, List<UserDTO> userDTOS, List<ProductVersionDTO> productVersionDTOS,
                              List<TestIssueFolderDTO> testIssueFolderDTOS, List<IssueStatusDTO> issueStatusDTOS) {
        this.lookupValueDTOS = lookupValueDTOS;
        this.userDTOS = userDTOS;
        this.productVersionDTOS = productVersionDTOS;
        this.testIssueFolderDTOS = testIssueFolderDTOS;
        this.issueStatusDTOS = issueStatusDTOS;
    }

    public List<LookupValueDTO> getLookupValueDTOS() {
        return lookupValueDTOS;
    }

    public void setLookupValueDTOS(List<LookupValueDTO> lookupValueDTOS) {
        this.lookupValueDTOS = lookupValueDTOS;
    }

    public List<UserDTO> getUserDTOS() {
        return userDTOS;
    }

    public void setUserDTOS(List<UserDTO> userDTOS) {
        this.userDTOS = userDTOS;
    }

    public List<ProductVersionDTO> getProductVersionDTOS() {
        return productVersionDTOS;
    }

    public void setProductVersionDTOS(List<ProductVersionDTO> productVersionDTOS) {
        this.productVersionDTOS = productVersionDTOS;
    }

    public List<TestIssueFolderDTO> getTestIssueFolderDTOS() {
        return testIssueFolderDTOS;
    }

    public void setTestIssueFolderDTOS(List<TestIssueFolderDTO> testIssueFolderDTOS) {
        this.testIssueFolderDTOS = testIssueFolderDTOS;
    }

    public List<IssueStatusDTO> getIssueStatusDTOS() {
        return issueStatusDTOS;
    }

    public void setIssueStatusDTOS(List<IssueStatusDTO> issueStatusDTOS) {
        this.issueStatusDTOS = issueStatusDTOS;
    }
}
