package cn.exrick.xboot.modules.your.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.modules.your.entity.Tenement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cn.exrick.xboot.common.vo.SearchVo;

import java.util.List;

/**
 * 物业接口
 * @author lind
 */
public interface TenementService extends XbootBaseService<Tenement, String> {

    /**
    * 多条件分页获取
    * @param tenement
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<Tenement> findByCondition(Tenement tenement, SearchVo searchVo, Pageable pageable);

}