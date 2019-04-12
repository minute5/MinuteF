package io.choerodon.test.manager.api.controller.v1

import com.alibaba.fastjson.JSONObject
import io.choerodon.agile.api.dto.CopyConditionDTO
import io.choerodon.agile.api.dto.IssueCreateDTO
import io.choerodon.agile.api.dto.IssueDTO
import io.choerodon.agile.api.dto.SearchDTO
import io.choerodon.core.domain.Page
import io.choerodon.test.manager.IntegrationTestConfiguration
import io.choerodon.test.manager.api.dto.IssueInfosDTO
import io.choerodon.test.manager.api.dto.TestCycleDTO
import io.choerodon.test.manager.api.dto.TestFolderRelQueryDTO
import io.choerodon.test.manager.api.dto.TestIssueFolderRelDTO
import io.choerodon.test.manager.app.service.TestCaseService
import io.choerodon.test.manager.app.service.TestIssueFolderRelService
import io.choerodon.test.manager.domain.test.manager.entity.TestCycleE
import io.choerodon.test.manager.domain.test.manager.entity.TestIssueFolderE
import io.choerodon.test.manager.infra.dataobject.TestIssueFolderDO
import io.choerodon.test.manager.infra.dataobject.TestIssueFolderRelDO
import io.choerodon.test.manager.infra.mapper.TestIssueFolderMapper
import io.choerodon.test.manager.infra.mapper.TestIssueFolderRelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Import
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT


/**
 * Created by zongw.lee@gmail.com
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(IntegrationTestConfiguration)
@Stepwise
class TestIssueFolderRelControllerSpec extends Specification {
    @Autowired
    TestCaseService testCaseService

    @Autowired
    TestIssueFolderRelService testIssueFolderRelService

    @Autowired
    TestIssueFolderRelMapper testIssueFolderRelMapper

    @Autowired
    TestIssueFolderMapper testIssueFolderMapper

    @Autowired
    TestRestTemplate restTemplate

    @Shared
    List objectVersionNumbers = new ArrayList()
    @Shared
    List foldersId = new ArrayList()
    @Shared
    def projectId = 1L
    @Shared
    def versionId = 1L

    @Shared
    TestIssueFolderDO resInsertDO

    def "InsertTestAndRelationship"() {
        given:
        //等等IssueCreateDTO测试数据
        IssueCreateDTO issueCreateDTO = new IssueCreateDTO()
        issueCreateDTO.setProjectId(projectId)
        issueCreateDTO.setTypeCode("issue_test")
        issueCreateDTO.setSummary("测试创建")
        IssueDTO issueDTO = new IssueDTO()
        issueDTO.setIssueId(11L)
        issueDTO.setObjectVersionNumber(1L)
        issueDTO.setProjectId(projectId)

        IssueCreateDTO issueCreateDTO2 = new IssueCreateDTO()
        issueCreateDTO2.setProjectId(projectId)
        issueCreateDTO2.setTypeCode("issue_test2")
        issueCreateDTO2.setSummary("测试创建2")
        IssueDTO issueDTO2 = new IssueDTO()
        issueDTO2.setIssueId(999999L)
        issueDTO2.setObjectVersionNumber(1L)
        issueDTO2.setProjectId(projectId)

        TestIssueFolderDO testIssueFolderDO = new TestIssueFolderDO()
        testIssueFolderDO.setName("testFolderForQueryIssuesById")
        testIssueFolderDO.setProjectId(projectId)
        testIssueFolderDO.setType(TestIssueFolderE.TYPE_CYCLE)
        testIssueFolderDO.setVersionId(versionId)
        testIssueFolderMapper.insert(testIssueFolderDO)
        resInsertDO = testIssueFolderMapper.selectOne(testIssueFolderDO)

        List list = testIssueFolderRelMapper.selectAll()
        println("list.size:" + list.size())
        for (TestIssueFolderRelDO testIssueFolderRelDO : list) {
            println("issueId:" + testIssueFolderRelDO.issueId)
        }

        TestIssueFolderDO folderDO = new TestIssueFolderDO()
        folderDO.setProjectId(444444444L)
        folderDO.setVersionId(444444444L)
        folderDO.setType("temp")
        folderDO.setName("testForGetDefaultFolder")
        testIssueFolderMapper.insert(folderDO)
        TestIssueFolderDO insertFolderDo = testIssueFolderMapper.selectOne(folderDO)

        IssueDTO issueDTO3 = new IssueDTO()
        issueDTO3.setIssueId(444444444L)
        issueDTO3.setObjectVersionNumber(1L)
        issueDTO3.setProjectId(444444444L)

        when: '向issueFolderRel的插入创建接口发请求'
        def entity = restTemplate.postForEntity('/v1/projects/{project_id}/issueFolderRel/testAndRelationship?folderId={folderId}&versionId={versionId}&applyType=test', issueCreateDTO, TestIssueFolderRelDTO, projectId, resInsertDO.getFolderId(), versionId)
        then:
        1 * testCaseService.createTest(_, _,_) >> issueDTO
        entity.statusCode.is2xxSuccessful()

        and:
        entity.body != null
        entity.body.issueId == 11L

        and: '设置值'
        objectVersionNumbers.add(entity.body.objectVersionNumber)
        foldersId.add(entity.body.folderId)

        when: '向issueFolderRel的插入创建接口发请求'
        def entity2 = restTemplate.postForEntity('/v1/projects/{project_id}/issueFolderRel/testAndRelationship?folderId={folderId}&versionId={versionId}&applyType=test', issueCreateDTO2, TestIssueFolderRelDTO, projectId, null, versionId)
        then:
        1 * testCaseService.createTest(_, _,_) >> issueDTO2
        entity2.statusCode.is2xxSuccessful()

        and:
        entity2.body != null
        entity2.body.issueId == 999999L

        when: '向testIssueFolderRel的插入接口发请求'
        def resultFailure = restTemplate.postForEntity('/v1/projects/{project_id}/issueFolderRel/testAndRelationship?folderId={folderId}&versionId={versionId}&applyType=test', issueCreateDTO, String.class, projectId, foldersId[0], versionId)
        then: '返回值'
        1 * testCaseService.createTest(_, _,_) >> issueDTO
        resultFailure.statusCode.is2xxSuccessful()
        JSONObject exceptionInfo = JSONObject.parse(resultFailure.body)
        assert exceptionInfo.get("failed").toString() == "true"
        assert exceptionInfo.get("code").toString() == "error.db.duplicateKey"

        when: '覆盖testIssueFolder的getDefaultFolderId方法中resultTestIssueFolderDTO不为空的情况'
        def entity3 = restTemplate.postForEntity('/v1/projects/{project_id}/issueFolderRel/testAndRelationship?folderId={folderId}&versionId={versionId}&applyType=test', issueCreateDTO, TestIssueFolderRelDTO, 444444444L, null, 444444444L)
        then:
        1 * testCaseService.createTest(_, _,_) >> issueDTO3
        entity3.statusCode.is2xxSuccessful()

        and:
        entity3.body != null
        entity3.body.issueId == 444444444L

    }

    def "InsertRelationship"() {
        given:
        TestIssueFolderRelDTO testIssueFolderRelDTO1 = new TestIssueFolderRelDTO()
        testIssueFolderRelDTO1.setProjectId(projectId)
        testIssueFolderRelDTO1.setIssueId(22L)
        testIssueFolderRelDTO1.setFolderId(foldersId[0])
        testIssueFolderRelDTO1.setVersionId(versionId)

        List<TestIssueFolderRelDTO> testIssueFolderRelDTOS = new ArrayList<>()
        testIssueFolderRelDTOS.add(testIssueFolderRelDTO1)

        TestIssueFolderRelDTO testIssueFolderRelDTO2 = new TestIssueFolderRelDTO()
        testIssueFolderRelDTO2.setProjectId(projectId)
        testIssueFolderRelDTO2.setIssueId(33L)
        testIssueFolderRelDTO2.setFolderId(1L)
        testIssueFolderRelDTO2.setVersionId(versionId)
        testIssueFolderRelDTOS.add(testIssueFolderRelDTO2)

        when: '向issueFolderRel的插入创建接口发请求'
        def entities = restTemplate.postForEntity('/v1/projects/{project_id}/issueFolderRel', testIssueFolderRelDTOS, List, projectId)
        then:
        entities.statusCode.is2xxSuccessful()
        entities.body.size() > 1

        when: '覆盖testIssueFolderRelDTOS为空的情况'
        entities = restTemplate.postForEntity('/v1/projects/{project_id}/issueFolderRel', new ArrayList<>(), List, projectId)
        then:
        entities.statusCode.is2xxSuccessful()
        entities.body.size() == 0
    }

    def "cloneOneIssue"() {
        given:
        List<Long> issuesId = new ArrayList<>()
        issuesId.add(11111L)

        TestIssueFolderRelDO testIssueFolderRelDO = new TestIssueFolderRelDO()
        testIssueFolderRelDO.setIssueId(11111L)


        when:
        HttpEntity requestEntity = new HttpEntity<>()
        def entity = restTemplate.exchange('/v1/projects/{project_id}/issueFolderRel/copy/issue/{issueId}',
                HttpMethod.PUT, requestEntity, TestIssueFolderRelDTO, projectId, 11L)

        then: '返回值'
        testCaseService.batchCloneIssue(_, _, _) >> issuesId
        List res = testIssueFolderRelMapper.select(testIssueFolderRelDO)

        and:
        entity.statusCode.is2xxSuccessful()
        res.size() == 1
        res.get(0).issueId == 11111L
    }

    def "QueryIssuesById"() {
        given:
        IssueInfosDTO issueInfosDTO = new IssueInfosDTO()
        issueInfosDTO.setIssueId(11L)
        IssueInfosDTO issueInfosDTO1 = new IssueInfosDTO()
        issueInfosDTO1.setIssueId(22L)
        Long[] issues = new Long[2]
        issues[0] = 11L
        issues[1] = 22L
        Map map = new HashMap()
        map.put(11L, issueInfosDTO)
        map.put(22L, issueInfosDTO1)

        Long[] exceptionIssues = new Long[2]
        exceptionIssues[0] = 11111L
        exceptionIssues[1] = 22222L

        def res = testIssueFolderMapper.selectAll()

        when: '向查询testIssueFolderRel的接口发请求'
        def entity = restTemplate.postForEntity('/v1/projects/{project_id}/issueFolderRel/query/by/issueId?folderId={folderId}&versionId={versionId}&organizationId=1', issues, Page.class, projectId, foldersId[0], versionId)

        then: '返回值'
        1 * testCaseService.getIssueInfoMap(_, _, _, _) >> map
        entity.statusCode.is2xxSuccessful()
        List detailFolderRelDTOS = entity.body

        expect: "设置期望值"
        detailFolderRelDTOS.size() == 2

        when: '向testIssueFolderRel的查询接口发请求'
        def resultFailure = restTemplate.postForEntity('/v1/projects/{project_id}/issueFolderRel/query/by/issueId?folderId={folderId}&versionId={versionId}&organizationId=1', exceptionIssues, Page.class, projectId, foldersId[0], versionId)
        then: '返回值'
        1 * testCaseService.getIssueInfoMap(_, _, _, _) >> map
        resultFailure.statusCode.is2xxSuccessful()
        assert resultFailure.body.isEmpty()

        when: '向testIssueFolderRel的查询接口发请求'
        resultFailure = restTemplate.postForEntity('/v1/projects/{project_id}/issueFolderRel/query/by/issueId?folderId={folderId}&versionId={versionId}&organizationId=1', issues, Page.class, projectId, foldersId[0], versionId)
        then: '返回值'
        1 * testCaseService.getIssueInfoMap(_, _, _, _) >> new HashMap<>()
        resultFailure.statusCode.is2xxSuccessful()
        assert resultFailure.body.isEmpty()

        when: '测试resultRelDTOS为空的情况'
        resultFailure = restTemplate.postForEntity('/v1/projects/{project_id}/issueFolderRel/query/by/issueId?folderId={folderId}&versionId={versionId}&organizationId=1', new ArrayList(), Page.class, projectId, foldersId[0], versionId)
        then: '返回值'
        resultFailure.statusCode.is2xxSuccessful()
        assert resultFailure.body.isEmpty()
    }

    def "QueryIssuesByParameter"() {
        given:
        IssueInfosDTO issueInfosDTO = new IssueInfosDTO()
        issueInfosDTO.setIssueId(11L)
        IssueInfosDTO issueInfosDTO1 = new IssueInfosDTO()
        issueInfosDTO1.setIssueId(22L)
        Integer[] issues = new Integer[2]
        issues[0] = 11
        issues[1] = 22
        Integer[] wrongIssues = new Integer[1]
        wrongIssues[0] = 1231231231
        Long[] longIssues = new Long[2]
        longIssues[0] = 11L
        longIssues[1] = 22L
        Map map = new HashMap()
        map.put(11L, issueInfosDTO)
        map.put(22L, issueInfosDTO1)

        SearchDTO searchDTO = new SearchDTO()
        Map searchMap = new HashMap()
        searchMap.put("issueIds", issues)
        searchDTO.setOtherArgs(searchMap)

        SearchDTO wrongSearchDTO = new SearchDTO()
        Map searchMap2 = new HashMap()
        searchMap2.put("issueIds", wrongIssues)
        wrongSearchDTO.setOtherArgs(searchMap2)

        TestFolderRelQueryDTO relQueryDTO = new TestFolderRelQueryDTO()
        relQueryDTO.setSearchDTO(searchDTO)

        TestFolderRelQueryDTO wrongRelQueryDTO = new TestFolderRelQueryDTO()
        wrongRelQueryDTO.setSearchDTO(wrongSearchDTO)

        TestFolderRelQueryDTO relQueryDTOWithNoIssueIds = new TestFolderRelQueryDTO()
        SearchDTO searchDTOWithNoIssueIds = new SearchDTO()
        searchDTOWithNoIssueIds.setOtherArgs(new HashMap())
        relQueryDTOWithNoIssueIds.setSearchDTO(searchDTOWithNoIssueIds)

        TestFolderRelQueryDTO relQueryDTOWithNoVersions = new TestFolderRelQueryDTO()

        TestFolderRelQueryDTO relQueryDTOWithVersions = new TestFolderRelQueryDTO()
        Long[] versionIds = new Long[1]
        versionIds[0] = versionId
        relQueryDTOWithVersions.setVersionIds(versionIds)

        when: '分支覆盖到searchDTO.getOtherArgs()不为空的情况'
        def entity = restTemplate.postForEntity('/v1/projects/{project_id}/issueFolderRel/query?folderId={folderId}&page={page}&size={size}&organizationId=1', relQueryDTO, Page.class, projectId, foldersId[0], 0, 1)
        then: '返回值'
        1 * testCaseService.queryIssueIdsByOptions(_, _) >> longIssues
        1 * testCaseService.getIssueInfoMap(_, _, _, _) >> map
        entity.statusCode.is2xxSuccessful()
        List detailFolderRelDTOS = entity.body
        expect: "设置期望值"
        detailFolderRelDTOS.size() == 1

        when: '分支覆盖到searchDTO.getOtherArgs()不为空的情况，但searchDTO.getOtherArgs().containsKey(sIssueIds)为假的情况'
        entity = restTemplate.postForEntity('/v1/projects/{project_id}/issueFolderRel/query?folderId={folderId}&page={page}&size={size}&organizationId=1', relQueryDTOWithNoIssueIds, Page.class, projectId, foldersId[0], 1, 1)
        then: '返回值'
        1 * testCaseService.queryIssueIdsByOptions(_, _) >> longIssues
        1 * testCaseService.getIssueInfoMap(_, _, _, _) >> map
        entity.statusCode.is2xxSuccessful()
        List detailFolderRelDTOS2 = entity.body
        expect: "设置期望值"
        detailFolderRelDTOS2.size() == 1


        when: '分支覆盖到searchDTO.getOtherArgs()为空且testFolderRelQueryDTO.getVersionIds为空的情况'
        entity = restTemplate.postForEntity('/v1/projects/{project_id}/issueFolderRel/query?folderId={folderId}&page={page}&size={size}&organizationId=1', relQueryDTOWithNoVersions, Page.class, projectId, foldersId[0], 1, 1)
        then: '返回值'
        1 * testCaseService.queryIssueIdsByOptions(_, _) >> longIssues
        1 * testCaseService.getIssueInfoMap(_, _, _, _) >> map
        entity.statusCode.is2xxSuccessful()
        List detailFolderRelDTOS3 = entity.body
        expect: "设置期望值"
        detailFolderRelDTOS3.size() == 1

        when: '分支覆盖到searchDTO.getOtherArgs()为空且testFolderRelQueryDTO.getVersionIds不为空的情况'
        entity = restTemplate.postForEntity('/v1/projects/{project_id}/issueFolderRel/query?folderId={folderId}&page={page}&size={size}&organizationId=1', relQueryDTOWithVersions, Page.class, projectId, foldersId[0], 1, 1)
        then: '返回值'
        1 * testCaseService.queryIssueIdsByOptions(_, _) >> longIssues
        1 * testCaseService.getIssueInfoMap(_, _, _, _) >> map
        entity.statusCode.is2xxSuccessful()
        List detailFolderRelDTOS4 = entity.body
        expect: "设置期望值"
        detailFolderRelDTOS4.size() == 1

        when: '分支覆盖到searchDTO.getOtherArgs()为空且testFolderRelQueryDTO.getVersionIds不为空的情况'
        entity = restTemplate.postForEntity('/v1/projects/{project_id}/issueFolderRel/query?folderId={folderId}&page={page}&size={size}&organizationId=1', wrongRelQueryDTO, Page.class, projectId, foldersId[0], 1, 1)
        then: '返回值'
        0 * testCaseService.queryIssueIdsByOptions(_, _) >> longIssues
        entity.statusCode.is2xxSuccessful()
        assert entity.body.isEmpty()

        when: '向testIssueFolderRel的查询接口发请求'
        def resultFailure = restTemplate.postForEntity('/v1/projects/{project_id}/issueFolderRel/query?folderId={folderId}&page={page}&size={size}&organizationId=1', relQueryDTO, Page.class, projectId, null, null, null, null)
        then: '返回值'
        1 * testCaseService.queryIssueIdsByOptions(_, _) >> new ArrayList<>()
        resultFailure.statusCode.is2xxSuccessful()
        assert resultFailure.body.isEmpty()
    }

    def "MoveIssue"() {
        given:
        //新文件夹2L
        foldersId.add(2L)
        IssueInfosDTO issueInfosDTO1 = new IssueInfosDTO()
        issueInfosDTO1.setIssueId(11L)
        issueInfosDTO1.setObjectVersionNumber(1L)
        IssueInfosDTO issueInfosDTO2 = new IssueInfosDTO()
        issueInfosDTO2.setIssueId(22L)
        issueInfosDTO2.setObjectVersionNumber(1L)
        List issueInfos = new ArrayList()
        issueInfos.add(issueInfosDTO1)
        issueInfos.add(issueInfosDTO2)
        TestIssueFolderRelDO origin = new TestIssueFolderRelDO()
        origin.setFolderId(foldersId[0])
        TestIssueFolderRelDO target = new TestIssueFolderRelDO()
        target.setFolderId(foldersId[1])

        IssueInfosDTO issueInfosDTO3 = new IssueInfosDTO()
        issueInfosDTO3.setIssueId(11L)
        issueInfosDTO3.setObjectVersionNumber(1L)
        List exceptionIssueInfos = new ArrayList()
        exceptionIssueInfos.add(issueInfosDTO3)

        when: '向查询testIssueFolderRel的接口发请求，将folder[0]的值移动到folder[1]中'
        restTemplate.put('/v1/projects/{project_id}/issueFolderRel/move?folderId={folderId}&versionId={versionId}', issueInfos, projectId, foldersId[1], versionId)

        then: '返回值'
        1 * testCaseService.batchIssueToVersionTest(_, _, _)
        List originIFR = testIssueFolderRelMapper.select(origin)
        List targetIFR = testIssueFolderRelMapper.select(target)

        and:
        println("ori.Size " + originIFR.size())
        println("ori:" + originIFR.get(0).getIssueId() + "|||" + originIFR.get(1).getIssueId())
        println("tar.Size " + targetIFR.size())
        println("tar:" + targetIFR.get(0).getIssueId() + "|||" + targetIFR.get(1).getIssueId())

        expect: '期望值'
        !originIFR.contains(issueInfosDTO1)
        !originIFR.contains(issueInfosDTO2)
        targetIFR.size() > 1
    }

    def "CopyIssue"() {
        given:
        IssueInfosDTO issueInfosDTO1 = new IssueInfosDTO()
        issueInfosDTO1.setIssueId(11L)
        IssueInfosDTO issueInfosDTO2 = new IssueInfosDTO()
        issueInfosDTO2.setIssueId(22L)
        List issueInfos = new ArrayList()
        issueInfos.add(issueInfosDTO1)
        issueInfos.add(issueInfosDTO2)
        TestIssueFolderRelDO origin = new TestIssueFolderRelDO()
        origin.setFolderId(foldersId[1])
        TestIssueFolderRelDO target = new TestIssueFolderRelDO()
        target.setFolderId(foldersId[0])
        List issues = new ArrayList()
        issues.add(44L)
        issues.add(55L)

        when: '向查询issues的接口发请求,将folder[1]的值复制到folder[0]中'
        restTemplate.put('/v1/projects/{project_id}/issueFolderRel/copy?folderId={folderId}&versionId={versionId}', issueInfos, projectId, foldersId[0], versionId)

        then: '返回值'
        testCaseService.batchCloneIssue(_, _, _) >> issues
        List originIFR = testIssueFolderRelMapper.select(origin)
        List targetIFR = testIssueFolderRelMapper.select(target)

        expect: '期望值'
        originIFR.size() < 4
        targetIFR.size() > 1

        when: '覆盖issueInfosDTOS为空的情况'
        restTemplate.put('/v1/projects/{project_id}/issueFolderRel/copy?folderId={folderId}&versionId={versionId}', new ArrayList(), projectId, foldersId[0], versionId)
        then: '返回值'
        List originIFR2 = testIssueFolderRelMapper.select(origin)
        List targetIFR2 = testIssueFolderRelMapper.select(target)
        expect: '期望值'
        originIFR2.size() < 4
        targetIFR2.size() > 1
    }

//    def "Delete"() {
//        given:
//        TestIssueFolderRelDO testIssueFolderRelDO1 = new TestIssueFolderRelDO()
//        testIssueFolderRelDO1.setIssueId(11L)
//        TestIssueFolderRelDO testIssueFolderRelDO2 = new TestIssueFolderRelDO()
//        testIssueFolderRelDO2.setIssueId(22L)
//        TestIssueFolderRelDO testIssueFolderRelDO3 = new TestIssueFolderRelDO()
//        testIssueFolderRelDO3.setIssueId(33L)
//        TestIssueFolderRelDO testIssueFolderRelDO4 = new TestIssueFolderRelDO()
//        testIssueFolderRelDO4.setIssueId(44L)
//        TestIssueFolderRelDO testIssueFolderRelDO5 = new TestIssueFolderRelDO()
//        testIssueFolderRelDO5.setIssueId(55L)
//        List issues = new ArrayList()
//        issues.add(11L)
//        issues.add(22L)
//        issues.add(33L)
//        issues.add(44L)
//        issues.add(55L)
//        when: '执行方法'
//        restTemplate.put('/v1/projects/{project_id}/issueFolderRel/delete', issues, projectId)
//
//        then: '返回值'
//        def result1 = testIssueFolderRelMapper.selectOne(testIssueFolderRelDO1)
//        def result2 = testIssueFolderRelMapper.selectOne(testIssueFolderRelDO2)
//        def result3 = testIssueFolderRelMapper.selectOne(testIssueFolderRelDO3)
//        def result4 = testIssueFolderRelMapper.selectOne(testIssueFolderRelDO4)
//        def result5 = testIssueFolderRelMapper.selectOne(testIssueFolderRelDO5)
//
//        expect: '期望值'
//        result1 == null
//        result2 == null
//        result3 == null
//        result4 == null
//        result5 == null
//    }
    def "Delete"() {
        given:
        TestIssueFolderRelDO testIssueFolderRelDO1 = new TestIssueFolderRelDO()
        testIssueFolderRelDO1.setIssueId(11L)
        TestIssueFolderRelDO testIssueFolderRelDO2 = new TestIssueFolderRelDO()
        testIssueFolderRelDO2.setIssueId(22L)
        TestIssueFolderRelDO testIssueFolderRelDO3 = new TestIssueFolderRelDO()
        testIssueFolderRelDO3.setIssueId(33L)
        TestIssueFolderRelDO testIssueFolderRelDO4 = new TestIssueFolderRelDO()
        testIssueFolderRelDO4.setIssueId(44L)
        TestIssueFolderRelDO testIssueFolderRelDO5 = new TestIssueFolderRelDO()
        testIssueFolderRelDO5.setIssueId(55L)
        List issues = new ArrayList()
        issues.add(11L)
        issues.add(22L)
        issues.add(33L)
        issues.add(44L)
        issues.add(55L)
        when: '执行方法'
        testIssueFolderRelService.delete(projectId,issues)

        then: '返回值'
        def result1 = testIssueFolderRelMapper.selectOne(testIssueFolderRelDO1)
        def result2 = testIssueFolderRelMapper.selectOne(testIssueFolderRelDO2)
        def result3 = testIssueFolderRelMapper.selectOne(testIssueFolderRelDO3)
        def result4 = testIssueFolderRelMapper.selectOne(testIssueFolderRelDO4)
        def result5 = testIssueFolderRelMapper.selectOne(testIssueFolderRelDO5)

        expect: '期望值'
        result1 == null
        result2 == null
        result3 == null
        result4 == null
        result5 == null
    }

}