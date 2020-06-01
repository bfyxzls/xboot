package cn.exrick.xboot.modules.your.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.modules.your.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cn.exrick.xboot.common.vo.SearchVo;

import java.util.List;

/**
 * 评价报告接口
 * @author lind
 */
public interface ReportService extends XbootBaseService<Report, String> {

    /**
    * 多条件分页获取
    * @param report
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<Report> findByCondition(Report report, SearchVo searchVo, Pageable pageable);

}