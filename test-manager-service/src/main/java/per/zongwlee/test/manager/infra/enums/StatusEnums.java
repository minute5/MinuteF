package per.zongwlee.test.manager.infra.enums;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/13
 */
public enum StatusEnums {

    backlog(0L),
    active(1L),
    finished(2L);

    private Long status;

    private StatusEnums(Long status){
        this.status = status;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
