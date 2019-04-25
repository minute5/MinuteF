package per.zongwlee.issue.api.dto;

import per.zongwlee.oauth.api.dto.RoleDTO;

import java.util.Date;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/12
 */
public class IssueDTO {

    private Long id;

    private String name;
    private Long type;
    private Long reporterId;
    private Long handlerId;
    private Long priorityId;
    private Long statusId;
    private Date handleDate;
    private Date solveDate;
    private Long branchId;

    private RoleDTO reporter;
    private RoleDTO handler;

    private PriorityDTO priority;

    public RoleDTO getReporter() {
        return reporter;
    }

    public void setReporter(RoleDTO reporter) {
        this.reporter = reporter;
    }

    public RoleDTO getHandler() {
        return handler;
    }

    public void setHandler(RoleDTO handler) {
        this.handler = handler;
    }

    public PriorityDTO getPriority() {
        return priority;
    }

    public void setPriority(PriorityDTO priority) {
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public void setReporterId(Long reporterId) {
        this.reporterId = reporterId;
    }

    public Long getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(Long handlerId) {
        this.handlerId = handlerId;
    }

    public Long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Long priorityId) {
        this.priorityId = priorityId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Date getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(Date handleDate) {
        this.handleDate = handleDate;
    }

    public Date getSolveDate() {
        return solveDate;
    }

    public void setSolveDate(Date solveDate) {
        this.solveDate = solveDate;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }
}
