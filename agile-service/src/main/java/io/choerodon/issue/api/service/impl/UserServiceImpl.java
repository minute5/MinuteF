package io.choerodon.issue.api.service.impl;

import io.choerodon.issue.api.dto.AttachmentDTO;
import io.choerodon.issue.api.dto.IssueDTO;
import io.choerodon.issue.api.dto.SearchDTO;
import io.choerodon.issue.api.dto.UserDTO;
import io.choerodon.issue.api.service.UserService;
import io.choerodon.issue.infra.feign.UserFeignClient;
import io.choerodon.issue.infra.feign.dto.UserInfo;
import io.choerodon.issue.infra.feign.dto.UserSearchDTO;
import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shinan.chen
 * @date 2018/9/10
 */
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public Map<Long, UserInfo> queryUserInfoMapByUserIds(List<Long> userIds) {
        List<UserDTO> userDTOS = userFeignClient.listUsersByIds(userIds.toArray(new Long[userIds.size()])).getBody();
        Map<Long, UserInfo> map = new HashMap<>();
        userDTOS.stream().forEach(x -> {
            map.put(x.getId(), new UserInfo(x.getId(), x.getRealName(), x.getEmail(), x.getImageUrl()));
        });
        return map;
    }


    @Override
    public void handleUserInfo(List<IssueDTO> issueDTOS) {
        List<Long> userIds = issueDTOS.stream().map(IssueDTO::getHandlerId).collect(Collectors.toList());
        userIds.addAll(issueDTOS.stream().map(IssueDTO::getReporterId).collect(Collectors.toList()));
        Map<Long, UserInfo> map = queryUserInfoMapByUserIds(userIds);
        for (IssueDTO issueDTO : issueDTOS) {
            if (issueDTO.getHandlerId() != null) {
                UserInfo userInfo = map.get(issueDTO.getHandlerId());
                if (userInfo != null) {
                    issueDTO.setHandlerName(userInfo.getRealName());
                    issueDTO.setHandlerImageUrl(userInfo.getImageUrl());
                    issueDTO.setHandlerEmail(userInfo.getEmail());
                }
            }
            if (issueDTO.getReporterId() != null) {
                UserInfo userInfo = map.get(issueDTO.getReporterId());
                if (userInfo != null) {
                    issueDTO.setReporterName(userInfo.getRealName());
                    issueDTO.setReporterImageUrl(userInfo.getImageUrl());
                    issueDTO.setReporterEmail(userInfo.getEmail());
                }
            }
        }
    }

    @Override
    public void handleAttachmentUser(List<AttachmentDTO> attachmentDTOS) {
        List<Long> userIds = attachmentDTOS.stream().map(AttachmentDTO::getUserId).collect(Collectors.toList());
        Map<Long, UserInfo> map = queryUserInfoMapByUserIds(userIds);
        for (AttachmentDTO attachmentDTO : attachmentDTOS) {
            if (attachmentDTO.getUserId() != null) {
                UserInfo userInfo = map.get(attachmentDTO.getUserId());
                if (userInfo != null) {
                    attachmentDTO.setUserName(userInfo.getRealName());
                    attachmentDTO.setUserImageUrl(userInfo.getImageUrl());
                }
            }
        }
    }
}
