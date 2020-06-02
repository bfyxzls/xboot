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
@Table(name = "evaluation_run_task")
@TableName("evaluation_run_task")
@ApiModel(value = "运行中的任务")
public class RunTask extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;
    private Long taskId;
    private Long taskTypeId;
}