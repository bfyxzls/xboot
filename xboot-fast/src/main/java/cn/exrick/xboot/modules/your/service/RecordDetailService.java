package cn.exrick.xboot.modules.your.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.entity.Court;
import cn.exrick.xboot.modules.your.entity.RecordDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 小区接口
 * @author lind
 */
public interface RecordDetailService extends XbootBaseService<RecordDetail, String> {

    /**
    * 多条件分页获取
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<RecordDetail> findByCondition(RecordDetail recordDetail, SearchVo searchVo, Pageable pageable);


}