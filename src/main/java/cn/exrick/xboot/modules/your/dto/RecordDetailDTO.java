package cn.exrick.xboot.modules.your.dto;

import cn.hutool.core.date.DateTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RecordDetailDTO {
    @ApiModelProperty(value = "评价试题ID，新增不赋值，修改时赋值")
    private String recordDetailId;
    @ApiModelProperty(value = "模版ID，新增操作需要为它赋值")
    private String templateId;
    @ApiModelProperty(value = "分数")
    private Double score;
    @ApiModelProperty(value = "文本选项")
    private String textValue;
    @ApiModelProperty(value = "文本内容")
    private String content;
    @ApiModelProperty(value = "照片内容")
    private String pictureUrl;
    @ApiModelProperty(value = "时间选项")
    private DateTime dateValue;
}
