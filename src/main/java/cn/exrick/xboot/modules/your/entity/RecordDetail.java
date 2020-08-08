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
import java.util.Date;
import java.util.List;

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
    @ApiModelProperty(value = "记录ID")
    private String recordId;
    @ApiModelProperty(value = "分类ID")
    private String typeId;
    @ApiModelProperty(value = "分类名称")
    private String typeTitle;
    @ApiModelProperty(value = "任务ID")
    private String taskId;
    @ApiModelProperty(value = "分数")
    private Double score;
    @ApiModelProperty(value = "文本选项")
    private String textValue;
    @ApiModelProperty(value = "文本内容")
    private String content;
    @ApiModelProperty(value = "照片内容")
    private String pictureUrl;
    @ApiModelProperty(value = "时间选项")
    private Date dateValue;
    private Integer questionType;
    @ApiModelProperty(value = "计分类型0不计分，1计分")
    private Integer scoreType;
    @ApiModelProperty(value = "子选项的值")
    @Transient
    @TableField(exist = false)
    private List<Template> valueTemplateList;
}