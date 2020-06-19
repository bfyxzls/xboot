package cn.exrick.xboot.modules.your.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel("提交评价DTO")
@Data
@ToString
public class RecordFormDTO {
    @ApiModelProperty("任务ID")
    private String taskId;
    @ApiModelProperty("选中的评价，格式：模板ID1_内容1|模板ID2_内容2|模板ID3_内容3")
    private String recordDetails;
    @ApiModelProperty(value = "小区ID")
    private String courtId;
    @ApiModelProperty(value = "分类ID")
    private String typeId;
}
