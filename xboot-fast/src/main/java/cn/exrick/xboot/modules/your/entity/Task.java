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
@Table(name = "evaluation_task")
@TableName("evaluation_task")
@ApiModel(value = "任务")
public class Task extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "名称")
    private String title;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "备注")
    private String description;

}