package cn.exrick.xboot.modules.your.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.modules.your.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cn.exrick.xboot.common.vo.SearchVo;

import java.util.List;

/**
 * 测试接口
 * @author lind
 */
public interface TaskService extends XbootBaseService<Task, String> {

    /**
    * 多条件分页获取
    * @param task
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<Task> findByCondition(Task task, SearchVo searchVo, Pageable pageable);

}