package cn.exrick.xboot.modules.your.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.modules.your.entity.Court;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cn.exrick.xboot.common.vo.SearchVo;

import java.util.List;

/**
 * 小区接口
 * @author lind
 */
public interface CourtService extends XbootBaseService<Court, String> {

    /**
    * 多条件分页获取
    * @param court
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<Court> findByCondition(Court court, SearchVo searchVo, Pageable pageable);

}