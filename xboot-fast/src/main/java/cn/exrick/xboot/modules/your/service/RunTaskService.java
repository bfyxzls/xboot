package cn.exrick.xboot.modules.your.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.modules.your.entity.RunTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cn.exrick.xboot.common.vo.SearchVo;

import java.util.List;

/**
 * 运行中的任务接口
 * @author lind
 */
public interface RunTaskService extends XbootBaseService<RunTask, String> {

    /**
    * 多条件分页获取
    * @param runTask
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<RunTask> findByCondition(RunTask runTask, SearchVo searchVo, Pageable pageable);

}