package cn.exrick.xboot.modules.your.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.modules.your.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cn.exrick.xboot.common.vo.SearchVo;

import java.util.List;

/**
 * 测试接口
 * @author lind
 */
public interface TypeService extends XbootBaseService<Type, String> {

    /**
    * 多条件分页获取
    * @param type
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<Type> findByCondition(Type type, SearchVo searchVo, Pageable pageable);

}