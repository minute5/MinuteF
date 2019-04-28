package per.zongwlee.agile.dto;

import javax.validation.constraints.NotNull;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/12
 */
public class PriorityDTO {

    private Long id;

    @NotNull(message = "名字不能为空")
    private String name;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
