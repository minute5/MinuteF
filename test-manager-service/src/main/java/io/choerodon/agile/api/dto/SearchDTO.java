package io.choerodon.agile.api.dto;

import io.choerodon.agile.infra.common.utils.StringUtil;

import javax.persistence.Transient;
import java.util.Map;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/5/31
 */
public class SearchDTO {

	/**
	 * 输入查询参数
	 */
	private Map<String, Object> searchArgs;

	/**
	 * 过滤查询参数
	 */
	private Map<String, Object> advancedSearchArgs;

	/**
	 * 关联查询参数
	 */
	private Map<String, Object> otherArgs;

	@Transient
	private Long[] executionStatus;

	@Transient
	private String[] defectStatus;

	/**
	 * issueNum+summary模糊搜索
	 */
	private String content;

	public Map<String, Object> getSearchArgs() {
		return searchArgs;
	}

	public void setSearchArgs(Map<String, Object> searchArgs) {
		this.searchArgs = searchArgs;
	}

	public Map<String, Object> getAdvancedSearchArgs() {
		return advancedSearchArgs;
	}

	public void setAdvancedSearchArgs(Map<String, Object> advancedSearchArgs) {
		this.advancedSearchArgs = advancedSearchArgs;
	}

	public Map<String, Object> getOtherArgs() {
		return otherArgs;
	}

	public void setOtherArgs(Map<String, Object> otherArgs) {
		this.otherArgs = otherArgs;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long[] getExecutionStatus() {
		return executionStatus;
	}

	public void setExecutionStatus(Long[] executionStatus) {
		this.executionStatus = executionStatus;
	}

	public String[] getDefectStatus() {
		return defectStatus;
	}

	public void setDefectStatus(String[] defectStatus) {
		this.defectStatus = defectStatus;
	}

	@Override
	public String toString() {
		return StringUtil.getToString(this);
	}
}
