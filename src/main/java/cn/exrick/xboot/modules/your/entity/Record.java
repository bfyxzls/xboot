package cn.exrick.xboot.modules.your.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import cn.exrick.xboot.common.utils.ExcelColumn;
import cn.exrick.xboot.modules.base.entity.Department;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "小区ID")
    private String courtId;
    @ApiModelProperty(value = "分类ID")
    private String typeId;
    @ApiModelProperty(value = "任务ID")
    private String taskId;
    @ApiModelProperty("组织机构ID")
    private String departmentId;
    @ApiModelProperty("上级组织机构列表，当前人所在组织包含这些组织的都是有权限的")
    private String departmentIds;
    @ApiModelProperty(value = "分值")
    @ExcelColumn(value = "分值", col = 3)
    private Double score;
    @ApiModelProperty(value = "状态：0未审核，1已审核")
    @ExcelColumn(value = "状态", col = 4)
    private Integer status;
    @ApiModelProperty("纬度")
    @ExcelColumn(value = "纬度", col = 5)
    private Double latitude;
    @ApiModelProperty("经度")
    @ExcelColumn(value = "经度", col = 6)
    private Double longitude;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "小区")
    private Court court;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "分类名称")
    private String typeTitle;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "任务名称")
    private String taskTitle;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "组织机构")
    private Department department;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "组织机构名称")
    @ExcelColumn(value = "组织机构", col = 1)
    private String departmentTreeTitle;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "小区名称")
    @ExcelColumn(value = "小区名称", col = 2)
    private String courtTitle;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "用户")
    private String createByName;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "用户")
    private String createByNickName;
}