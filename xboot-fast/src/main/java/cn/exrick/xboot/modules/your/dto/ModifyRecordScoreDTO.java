package cn.exrick.xboot.modules.your.dto;

import cn.exrick.xboot.modules.your.entity.RecordDetail;
import lombok.Data;
import lombok.ToString;

import java.util.List;
@Data
@ToString
public class ModifyRecordScoreDTO {
    private List<RecordDetail> recordDetails;
}
