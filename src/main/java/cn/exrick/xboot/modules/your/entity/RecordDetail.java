package cn.exrick.xboot.modules.your.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author lind
 */
@Data
@Entity
@Table(name = "evaluation_record_detail")
@TableName("evaluation_record_detail")
@ApiModel(value = "小区")
@NoArgsConstructor
@AllArgsConstructor
public class RecordDetail extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "模版标题")
    private String templateTitle;
    @ApiModelProperty(value = "模版ID")
    private String templateId;
    @ApiModelProperty(value = "建立部门ID")
    private String createDepartmentId;
    @ApiModelProperty(value = "分数")
    private Double score;
    @ApiModelProperty(value = "记录ID")
    private String recordId;
    @ApiModelProperty(value = "分类ID")
    private String typeId;
    @ApiModelProperty(value = "分类名称")
    private String typeTitle;
    @ApiModelProperty(value = "任务ID")
    private String taskId;
    @ApiModelProperty(value = "文本内容")
    private String content;
    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "试题类型")
    private Integer questionType;

}