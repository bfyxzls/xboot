package cn.exrick.xboot.modules.your.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import cn.exrick.xboot.modules.base.entity.Role;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author lind
 */
@Data
@Entity
@Table(name = "evaluation_task_type")
@TableName("evaluation_task_type")
@ApiModel(value = "任务与类型关系")
public class TaskType extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("任务")
    private String taskId;
    @ApiModelProperty("分类")
    private String typeId;
    @ApiModelProperty("限次")
    private Integer limitCount;
    @ApiModelProperty("所占百分比")
    private Integer percent;
    @ApiModelProperty("备注")
    private String description;
    @ApiModelProperty("排序")
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;
    @ApiModelProperty(value = "这个任务的分类可以被哪个角色看到")
    private String roleIds;
    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "任务名称")
    private String taskTitle;
    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "分类名称")
    private String typeTitle;
    @ApiModelProperty(value = "这个任务的分类可以被哪个角色看到")
    @Transient
    @TableField(exist=false)
    private String roleNames;
    @ApiModelProperty(value = "这个任务的分类可以被哪个角色看到")
    @Transient
    @TableField(exist=false)
    private List<Role> roles;
    @ApiModelProperty(value = "分类下面的评价模板列表")
    @Transient
    @TableField(exist=false)
    private List<Template> templates;
}