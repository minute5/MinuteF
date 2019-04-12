package io.choerodon.test.manager.app.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelImportService {

    boolean cancelFileUpload(Long historyId);

    void downloadImportTemp(HttpServletRequest request, HttpServletResponse response);

    void importIssueByExcel(Long organizationId, Long projectId, Long versionId, Long userId, Workbook workbook);
}
