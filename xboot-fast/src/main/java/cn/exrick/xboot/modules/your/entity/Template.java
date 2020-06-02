package cn.exrick.xboot.modules.your.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author lind
 */
@Data
@Entity
@Table(name = "evaluation_template")
@TableName("evaluation_template")
@ApiModel(value = "评价模板")
public class Template extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;
    private String title;
    private String description;
    private Long typeId;
    private String parentId;
    private String content;
    private Integer score;
    private Boolean isParent;
    private String parentTitle;
    private Integer sortOrder;
}