package cn.exrick.xboot.modules.your.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import cn.exrick.xboot.modules.base.entity.Permission;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author lind
 */
@Data
@Entity
@Table(name = "evaluation_template")
@TableName("evaluation_template")
@ApiModel(value = "评价模板")
public class Template extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "名称")
    private String title;
    @ApiModelProperty(value = "备注")
    private String description;
    @ApiModelProperty(value = "分类ID")
    private String typeId;
    @ApiModelProperty(value = "上级ID")
    private String parentId;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "分值")
    private Integer score;
    @ApiModelProperty(value = "排序")
    private Integer sortOrder;
    @ApiModelProperty(value = "级别")
    private Integer level;
    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "是否为上级")
    private Boolean isParent;
    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "上级名称")
    private String parentTitle;
    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "孩子")
    private List<Template> children;
    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "分类名称")
    private String typeTitle;

}