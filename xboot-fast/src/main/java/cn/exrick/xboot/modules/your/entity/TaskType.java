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
@Table(name = "evaluation_task_type")
@TableName("evaluation_task_type")
@ApiModel(value = "任务与类型关系")
public class TaskType extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;
    private Long taskId;
    private Long typeId;
    private Integer limit;
    private Integer percent;
    private String description;

}