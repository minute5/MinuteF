package per.zongwlee.oauth.infra.enums;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/10
 */
public enum RoleType {
    //0：普通开发用户，1：主管--管理员，2：Minute5总管,3：经理--超级管理员，4：试用人员
    ORDINARY(0),
    MANAGER(1),
    ROOT(2),
    SUPERMANAGER(3),
    PROBATIONER(4);

    private int key;

    private RoleType(int key){
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
