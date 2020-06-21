package cn.exrick.xboot.modules.your.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.entity.Expert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 小区接口
 *
 * @author lind
 */
public interface ExpertService extends XbootBaseService<Expert, String> {

    /**
     * 多条件分页获取
     *
     * @param expert
     * @param searchVo
     * @param pageable
     * @return
     */
    Page<Expert> findByCondition(Expert expert, SearchVo searchVo, Pageable pageable);

}