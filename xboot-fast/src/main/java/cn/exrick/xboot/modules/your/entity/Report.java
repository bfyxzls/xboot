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
    private Long tenementId;
    private Long courtId;
    private Long typeId;
    private Long taskId;
    private Integer score;
    private LocalDate totalDate;
    private String fileUrl;
    private Integer totalCount;
}