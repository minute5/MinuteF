package io.choerodon.test.manager.domain.test.manager.entity;

import io.choerodon.agile.infra.common.utils.RankUtil;
import io.choerodon.test.manager.domain.repository.TestCaseStepRepository;
import io.choerodon.test.manager.infra.common.utils.SpringUtil;
import io.choerodon.test.manager.infra.dataobject.TestCycleCaseAttachmentRelDO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * Created by 842767365@qq.com on 6/11/18.
 */
@Component
@Scope("prototype")
public class TestCaseStepE {
    private Long stepId;

    private String rank;

    private Long issueId;

    private String testStep;

    private String testData;

    private String expectedResult;

    private Long objectVersionNumber;

    private String lastRank;

    private String nextRank;

    private Long createdBy;

    private Long lastUpdatedBy;

    private Date creationDate;

    private Date lastUpdateDate;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    private List<TestCycleCaseAttachmentRelDO> attachments;

    @Autowired
    private TestCaseStepRepository testCaseStepRepository;

    public TestCaseStepE createOneStep() {
        if (lastRank == null) {
            lastRank = getLastedStepRank();
        }

        setRank(RankUtil.Operation.INSERT.getRank(lastRank, nextRank));
        return addSelf();
    }

    public static List<TestCaseStepE> createSteps(List<TestCaseStepE> testCaseSteps) {
        TestCaseStepE currentStep = testCaseSteps.get(0);
        currentStep.setRank(RankUtil.Operation.INSERT.getRank(currentStep.getLastedStepRank(), null));
        TestCaseStepE prevStep = currentStep;

        for (int i = 1; i < testCaseSteps.size(); i++) {
            currentStep = testCaseSteps.get(i);
            currentStep.setRank(RankUtil.Operation.INSERT.getRank(prevStep.rank, null));
            prevStep = currentStep;
        }

        TestCaseStepRepository repository = SpringUtil.getApplicationContext().getBean(TestCaseStepRepository.class);
        return repository.batchInsert(testCaseSteps);
    }

    public String getLastedStepRank() {
        Assert.notNull(issueId, "error.case.step.insert.issueId.not.null");
        return testCaseStepRepository.getLastedRank(issueId);
    }

    public TestCaseStepE changeOneStep() {
        if (!StringUtils.isEmpty(lastRank) || !StringUtils.isEmpty(nextRank)) {
            setRank(RankUtil.Operation.UPDATE.getRank(lastRank, nextRank));
        }
        return updateSelf();
    }

    public List<TestCaseStepE> querySelf() {
        return testCaseStepRepository.query(this);
    }

    public List<TestCaseStepE> queryByParameter() {
        return testCaseStepRepository.queryByParameter(this);
    }

    public TestCaseStepE addSelf() {
        return testCaseStepRepository.insert(this);
    }

    public TestCaseStepE updateSelf() {
        return testCaseStepRepository.update(this);
    }

    public void deleteSelf() {
        testCaseStepRepository.delete(this);
    }


    public Long getStepId() {
        return stepId;
    }

    public String getRank() {
        return rank;
    }

    public Long getIssueId() {
        return issueId;
    }

    public String getTestStep() {
        return testStep;
    }

    public String getTestData() {
        return testData;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public Long getObjectVersionNumber() {
        return objectVersionNumber;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public void setTestStep(String testStep) {
        this.testStep = testStep;
    }

    public void setTestData(String testData) {
        this.testData = testData;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }

    public void setTestCaseStepRepository(TestCaseStepRepository testCaseStepRepository) {
        this.testCaseStepRepository = testCaseStepRepository;
    }

    public String getLastRank() {
        return lastRank;
    }

    public void setLastRank(String lastRank) {
        this.lastRank = lastRank;
    }

    public String getNextRank() {
        return nextRank;
    }

    public void setNextRank(String nextRank) {
        this.nextRank = nextRank;
    }

    public TestCaseStepRepository getTestCaseStepRepository() {
        return testCaseStepRepository;
    }

    public List<TestCycleCaseAttachmentRelDO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TestCycleCaseAttachmentRelDO> attachments) {
        this.attachments = attachments;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
