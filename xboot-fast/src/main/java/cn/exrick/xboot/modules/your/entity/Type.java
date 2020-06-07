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
@Table(name = "evaluation_type")
@TableName("evaluation_type")
@ApiModel(value = "测试")
public class Type extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("名称")
    private String title;
    @ApiModelProperty("备注")
    private String description;

}