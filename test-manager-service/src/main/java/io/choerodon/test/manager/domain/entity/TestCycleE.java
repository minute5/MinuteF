package io.choerodon.test.manager.domain.entity;

import io.choerodon.test.manager.domain.repository.TestCycleRepository;
import io.choerodon.test.manager.domain.test.manager.factory.TestCycleEFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 842767365@qq.com on 6/11/18.
 */
@Component
@Scope("prototype")
public class TestCycleE {

	public static final String FOLDER = "folder";
	public static final String CYCLE = "cycle";
	public static final String TEMP = "temp";
	public static final String TEMP_CYCLE_NAME = "临时";

    private Long cycleId;

    private Long parentCycleId;

    private String cycleName;

    private Long versionId;

    private String description;

    private String build;

    private String environment;

    private Date fromDate;

    private Date toDate;

    private String type;

    private Long objectVersionNumber;

	private Long createdBy;

	private CountMap cycleCaseList;

    private Long folderId;

    private Long lastUpdatedBy;

    @Autowired
    TestCycleRepository testCycleRepository;

    public List<TestCycleE> queryAll(){
        return testCycleRepository.queryAll();
    }

    public List<TestCycleE> querySelf() {
        return testCycleRepository.query(this);
    }

    public List<TestCycleE> queryChildCycle() {
        return testCycleRepository.queryChildCycle(this);
    }

	public TestCycleE queryOne() {
		return testCycleRepository.queryOne(this);
	}


	public TestCycleE cloneCycle(TestCycleE proto) {
		parentCycleId = Optional.ofNullable(parentCycleId).orElse(proto.getParentCycleId());
		cycleName = Optional.ofNullable(cycleName).orElse(proto.getCycleName());
		versionId = Optional.ofNullable(versionId).orElse(proto.getVersionId());
		description = Optional.ofNullable(description).orElse(proto.getDescription());
		build = Optional.ofNullable(build).orElse(proto.getBuild());
		environment = Optional.ofNullable(environment).orElse(proto.getEnvironment());
		fromDate = Optional.ofNullable(fromDate).orElse(proto.getFromDate());
		toDate = Optional.ofNullable(toDate).orElse(proto.getToDate());
		type = Optional.ofNullable(type).orElse(proto.getType());
		folderId = Optional.ofNullable(folderId).orElse(proto.getFolderId());
		return addSelf();
	}

	public List<TestCycleE> getChildFolder() {
		TestCycleE testCycleE = TestCycleEFactory.create();
		testCycleE.setParentCycleId(cycleId);
		testCycleE.setType(FOLDER);
		return testCycleE.querySelf();
	}

	public List<TestCycleE> getChildFolder(List<TestCycleE> testCycleES) {
		return testCycleES.stream().filter(v -> this.cycleId.equals(v.getParentCycleId())  && v.getType().equals(FOLDER)).collect(Collectors.toList());
	}

	public List<TestCycleE> querySelfWithBar(Long[] versionIds,Long assignedTo) {
		return testCycleRepository.queryBar(versionIds,assignedTo);
    }


    public TestCycleE addSelf() {
        return testCycleRepository.insert(this);
    }

    public TestCycleE updateSelf() {
        return testCycleRepository.update(this);
    }

    public void deleteSelf() {
        testCycleRepository.delete(this);
    }

    public Long getCycleId() {
        return cycleId;
    }

    public Long getParentCycleId() {
        return parentCycleId;
    }

    public String getCycleName() {
        return cycleName;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public String getDescription() {
        return description;
    }

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getBuild() {
        return build;
    }

    public String getEnvironment() {
        return environment;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public String getType() {
        return type;
    }

    public Long getObjectVersionNumber() {
        return objectVersionNumber;
    }

	public Map getCycleCaseList() {
		return cycleCaseList;
	}

	public void setCycleCaseList(List<Map<String, Object>> cycleCaseList) {
		CountMap map = new CountMap();
		cycleCaseList.forEach(v ->
			map.put((String) v.get("color"), (Long) v.get("counts"))
		);
		this.cycleCaseList = map;
	}

    public void setCycleId(Long cycleId) {
        this.cycleId = cycleId;
    }

    public void setParentCycleId(Long parentCycleId) {
        this.parentCycleId = parentCycleId;
    }

    public void setCycleName(String cycleName) {
        this.cycleName = cycleName;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }

	public void countChildStatus(List<TestCycleE> cycleCaseList) {
		cycleCaseList.forEach(v ->
			this.cycleCaseList.merge(v.getCycleCaseList())
		);

	}

	private static class CountMap extends HashMap<String, Long> {
		private void merge(Map<String, Long> plus) {
			plus.forEach((k, v) -> {
				if (this.containsKey(k)) {
					this.put(k, super.get(k) + v);
				} else {
					this.put(k, v);
				}
			});
		}
	}

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
