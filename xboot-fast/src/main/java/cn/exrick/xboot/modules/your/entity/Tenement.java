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
@Table(name = "evaluation_tenement")
@TableName("evaluation_tenement")
@ApiModel(value = "物业")
public class Tenement extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;
    private String title;
    private String description;

}