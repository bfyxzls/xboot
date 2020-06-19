package cn.exrick.xboot.modules.your.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.modules.your.entity.TaskType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cn.exrick.xboot.common.vo.SearchVo;

import java.util.List;

/**
 * 任务与类型关系接口
 * @author lind
 */
public interface TaskTypeService extends XbootBaseService<TaskType, String> {

    /**
    * 多条件分页获取
    * @param taskType
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<TaskType> findByCondition(TaskType taskType, SearchVo searchVo, Pageable pageable);

}