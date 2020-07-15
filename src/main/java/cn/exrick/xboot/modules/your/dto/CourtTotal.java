package cn.exrick.xboot.modules.your.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 小区统计.
 */
@Data
@ToString
public class CourtTotal {
    @ApiModelProperty(value = "小区ID")
    private String courtId;
    @ApiModelProperty(value = "本人提交数")
    private Long selfCount;
    @ApiModelProperty(value = "共评价份数")
    private Long totalCount;
    @ApiModelProperty(value = "预订评价份数")
    private Integer planCount;
}
