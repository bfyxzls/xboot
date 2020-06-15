package cn.exrick.xboot.modules.your.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

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
}