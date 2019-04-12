package io.choerodon.test.manager.api.controller.v1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import io.choerodon.test.manager.domain.test.manager.entity.TestAppInstanceE;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.choerodon.core.exception.CommonException;
import io.choerodon.swagger.annotation.Permission;
import io.choerodon.test.manager.app.service.JsonImportService;
import io.choerodon.test.manager.app.service.TestAppInstanceService;
import io.choerodon.test.manager.infra.common.utils.FileUtil;

@RestController
@RequestMapping("/v1/automation")
public class TestAutomationController {

    private static final Logger logger = LoggerFactory.getLogger(TestAutomationController.class);

    @Autowired
    private JsonImportService jsonImportService;

    @Autowired
    private TestAppInstanceService appInstanceService;

    @Permission(permissionPublic = true)
    @ApiOperation("从导入自动化测试报告")
    @PostMapping("/import/report/mocha")
    public ResponseEntity<Long> importMochaReport(@RequestParam String releaseName,
                                                  @RequestParam("file") MultipartFile file) {
        byte[] bytes;
        try {
            bytes = FileUtil.unTarGzToMemory(file.getInputStream()).get(0);
        } catch (IOException e) {
            throw new CommonException("error.decompress.tarGz", e);
        }

        try {
            return new ResponseEntity<>(jsonImportService.importMochaReport(releaseName,
                    new String(bytes, StandardCharsets.UTF_8)), HttpStatus.CREATED);
        } catch (Throwable e) {
            appInstanceService.shutdownInstance(Long.parseLong(TestAppInstanceE.getInstanceIDFromReleaseName(releaseName)));
            logger.error("导入mocha测试报告失败，测试状态置为失败", e);
            throw new CommonException("error.automation.import.mocha.report");
        }
    }
}
