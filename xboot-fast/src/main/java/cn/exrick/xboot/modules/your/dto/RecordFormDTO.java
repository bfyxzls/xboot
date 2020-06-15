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
    @ApiModelProperty("选中的评价，格式：模板ID1_得分1|模板ID2_得分2|模板ID3_得分3")
    private String recordDetails;
    @ApiModelProperty(value = "物业ID")
    private String tenementId;
    @ApiModelProperty(value = "小区ID")
    private String courtId;
    @ApiModelProperty(value = "分类ID")
    private String typeId;

    @ApiModelProperty(value = "名称")
    private String title;
    @ApiModelProperty(value = "照片地址")
    private String pictureUrl;
    @ApiModelProperty(value = "居住楼号")
    private String addressCode;
    @ApiModelProperty(value = "入住小区时间")
    private String joinTime;
    @ApiModelProperty(value = "身份")
    private String identity;
}
