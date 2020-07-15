package cn.exrick.xboot.modules.your.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ApiModel("提交评价DTO")
@Data
@ToString
public class RecordFormDTO {
    @ApiModelProperty("任务ID")
    private String taskId;
    @ApiModelProperty("选中的评价")
    private List<RecordDetailDTO> jsonRecordDetails;
    @ApiModelProperty(value = "小区ID")
    private String courtId;
    @ApiModelProperty(value = "分类ID")
    private String typeId;
    @ApiModelProperty("纬度")
    private Double latitude;
    @ApiModelProperty("经度")
    private Double longitude;
}
