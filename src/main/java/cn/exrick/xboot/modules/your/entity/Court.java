package cn.exrick.xboot.modules.your.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
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
@Table(name = "evaluation_court")
@TableName("evaluation_court")
@ApiModel(value = "小区")
public class Court extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("建立者ID")
    private String createDepartmentId;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("物业ID")
    private String tenementId;
    @ApiModelProperty("组织机构ID")
    private String departmentId;
    @ApiModelProperty("上级组织机构ID")
    private String departmentIds;
    @ApiModelProperty("纬度")
    private Double latitude;
    @ApiModelProperty("经度")
    private Double longitude;
    @ApiModelProperty("编号 ")
    private String villageNumber;
    @ApiModelProperty("详细地址")
    private String address;
    @ApiModelProperty("所属区")
    private String region;
    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "组织机构")
    private String departmentTitle;
    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "物业")
    private String tenementTitle;
    @ApiModelProperty(value = "项目类型，商品房|保障房")
    private String  projectType;

}