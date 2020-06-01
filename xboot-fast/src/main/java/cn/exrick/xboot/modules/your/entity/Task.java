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
@Table(name = "evaluation_task")
@TableName("evaluation_task")
@ApiModel(value = "测试")
public class Task extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;
    private String title;
    private Integer status;
}