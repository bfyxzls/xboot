package cn.exrick.xboot.modules.your.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.dto.CourtTotal;
import cn.exrick.xboot.modules.your.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 评价记录接口
 *
 * @author lind
 */
public interface RecordService extends XbootBaseService<Record, String> {

    /**
     * 多条件分页获取
     *
     * @param record
     * @param searchVo
     * @param pageable
     * @return
     */
    Page<Record> findByCondition(boolean isSelf, Record record, SearchVo searchVo, Pageable pageable);
    /**
     * 得到小区的统计.
     * @param courtId .
     * @return
     */
    CourtTotal getRecordCourtTotal(String courtId);
}