package cn.exrick.xboot.modules.your.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author lind
 */
@Data
@Entity
@Table(name = "evaluation_record")
@TableName("evaluation_record")
@ApiModel(value = "评价记录")
public class Record extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "建立人部门")
    private String createDepartmentId;
    @ApiModelProperty(value = "物业ID")
    private String tenementId;
    @ApiModelProperty(value = "小区ID")
    private String courtId;
    @ApiModelProperty(value = "分类ID")
    private String typeId;
    @ApiModelProperty(value = "任务ID")
    private String taskId;
    @ApiModelProperty(value = "分值")
    private Integer score;
    @ApiModelProperty(value = "名称")
    private String title;
    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "物业名称")
    private String tenementTitle;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "小区名称")
    private String courtTitle;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "分类名称")
    private String typeTitle;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "任务名称")
    private String taskTitle;
}