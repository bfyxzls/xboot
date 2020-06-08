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
    private String taskId;
    private String taskTypeId;
    @ApiModelProperty("流程 1业务评价 2专家评价 3审核评价 4完成生成报告")
    private Integer flow;
}