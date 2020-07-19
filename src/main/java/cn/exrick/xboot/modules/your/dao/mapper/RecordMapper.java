package cn.exrick.xboot.modules.your.dao.mapper;

import cn.exrick.xboot.modules.your.entity.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author Exrickx
 */
public interface RecordMapper extends BaseMapper<Record> {

    /**
     * 业主报表.
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> exportExcelYezhu(Map<String, Object> params);

    /**
     * 专家报表.
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> exportExcelZhuanjia(Map<String, Object> params);

    /**
     * 管理报表.
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> exportExcelGuanli(Map<String, Object> params);

}
