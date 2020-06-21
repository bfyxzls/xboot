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
@Table(name = "evaluation_expert")
@TableName("evaluation_expert")
@ApiModel(value = "专家表")
public class Expert extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "专家姓名")
    private String name;
    @ApiModelProperty(value = "专家类型")
    private String type;
    @ApiModelProperty(value = "学历")
    private String education;
    @ApiModelProperty(value = "个人简介")
    private String introduction;
    @ApiModelProperty(value = "照片")
    private String picture;
    @ApiModelProperty(value = "个人经历")
    private String experience;
    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "专家类型")
    private String typeTitle;
    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "学历")
    private String educationTitle;
}