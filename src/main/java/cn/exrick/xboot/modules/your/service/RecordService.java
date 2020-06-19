package cn.exrick.xboot.modules.your.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.modules.your.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cn.exrick.xboot.common.vo.SearchVo;

import java.util.List;

/**
 * 评价记录接口
 * @author lind
 */
public interface RecordService extends XbootBaseService<Record, String> {

    /**
    * 多条件分页获取
    * @param record
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<Record> findByCondition(Record record, SearchVo searchVo, Pageable pageable);

}