package cn.exrick.xboot.modules.your.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * @author lind
 */
@Data
@Entity
@Table(name = "evaluation_report")
@TableName("evaluation_report")
@ApiModel(value = "评价报告")
public class Report extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;
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
    @ApiModelProperty(value = "统计日期")
    private LocalDate totalDate;
    @ApiModelProperty(value = "附件报告文件地址")
    private String fileUrl;
    @ApiModelProperty(value = "统计数量")
    private Integer totalCount;
    @ApiModelProperty(value = "访问员名称")
    private String visitName;
}