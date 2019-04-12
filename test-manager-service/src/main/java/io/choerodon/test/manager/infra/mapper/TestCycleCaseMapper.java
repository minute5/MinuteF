package io.choerodon.test.manager.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import io.choerodon.test.manager.infra.dataobject.TestCycleCaseDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by 842767365@qq.com on 6/11/18.
 */
public interface TestCycleCaseMapper extends BaseMapper<TestCycleCaseDO> {
    List<TestCycleCaseDO> queryWithAttachAndDefect(@Param("dto")TestCycleCaseDO testCycleCaseDO,@Param("page")int page,@Param("pageSize")int pageSize);

	/**
	 *	查询为父cycle的所有子阶段的信息
	 * @param testCycleCaseDOS
	 * @param page
	 * @param pageSize
	 * @return
	 */
	List<TestCycleCaseDO> queryByFatherCycleWithAttachAndDefect(@Param("dtos")List<TestCycleCaseDO> testCycleCaseDOS,@Param("page")int page,@Param("pageSize")int pageSize);

	List<TestCycleCaseDO> queryByFatherCycleWithAttachAndDefect_oracle(@Param("dtos")List<TestCycleCaseDO> testCycleCaseDOS,@Param("page")int page,@Param("pageSize")int pageSize);

    List<TestCycleCaseDO> queryWithAttachAndDefect_oracle(@Param("dto")TestCycleCaseDO testCycleCaseDO,@Param("page")int page,@Param("pageSize")int pageSize);

    Long queryWithAttachAndDefect_count(@Param("dto")TestCycleCaseDO testCycleCaseDO);

	List<TestCycleCaseDO> filter(Map map);

	List<TestCycleCaseDO> queryByIssue(@Param("issueId") Long issueId);


	/**查询issues下的cycleCase
	 * @param issueId
	 * @return
	 */
	List<TestCycleCaseDO> queryInIssues(@Param("ids") Long[] issueId);

	List<TestCycleCaseDO> queryCaseAllInfoInCyclesOrVersions(@Param("cycleIds") Long[] cycleIds, @Param("versionIds") Long[] versionIds);

	List<TestCycleCaseDO> queryCycleCaseForReporter(@Param("ids") Long[] issueIds);

	Long countCaseNotRun(@Param("ids") Long[] cycleIds);

	/** 统计cycleIds下所有已经启动的Issue的数量
	 * @param cycleIds
	 * @return
	 */
	Long countCaseNotPlain(@Param("ids") Long[] cycleIds);

	/** 获取cycleIds下所有的测试实例数量
	 * @param cycleIds
	 * @return
	 */
	Long countCaseSum(@Param("ids") Long[] cycleIds);

	/**获取cycle（除了temp类型）下是否存的同issue的测试用例
	 * @param testCycleCase
	 * @return
	 */
	Long validateCycleCaseInCycle(TestCycleCaseDO testCycleCase);

	/**获取cycle下最后一个case的rank
	 * @param cycleId
	 * @return
	 */
	String getLastedRank(@Param("cycleId") Long cycleId);

	String getLastedRank_oracle(@Param("cycleId") Long cycleId);

	int batchInsertTestCycleCases(List<TestCycleCaseDO> testCycleCaseDOs);
}
