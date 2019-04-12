package io.choerodon.issue.api.service.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.issue.api.dto.IssueDTO;
import io.choerodon.issue.api.dto.IssueFieldValueDTO;
import io.choerodon.issue.api.dto.SearchDTO;
import io.choerodon.issue.api.service.IssueService;
import io.choerodon.issue.api.service.UserService;
import io.choerodon.issue.domain.Issue;
import io.choerodon.issue.infra.mapper.*;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.service.BaseServiceImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Arrays;
import java.util.List;

/**
 * @author shinan.chen
 * @Date 2018/9/3
 */
@Component
@RefreshScope
public class IssueServiceImpl extends BaseServiceImpl<Issue> implements IssueService {
    @Value("${spring.application.name:default}")
    private String serverCode;
    @Autowired
    private IssueMapper issueMapper;


    private final ModelMapper modelMapper = new ModelMapper();





}
