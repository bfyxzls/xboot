package cn.exrick.xboot.modules.your.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.dto.RecordFormDTO;
import cn.exrick.xboot.modules.your.entity.RecordDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 小区接口
 *
 * @author lind
 */
public interface RecordDetailService extends XbootBaseService<RecordDetail, String> {

    /**
     * 多条件分页获取.
     *
     * @param searchVo
     * @param pageable
     * @return
     */
    Page<RecordDetail> findByCondition(RecordDetail recordDetail, SearchVo searchVo, Pageable pageable);

    /**
     * 根据recordid查询.
     *
     * @param recordId
     * @return
     */
    List<RecordDetail> findByRecordId(String recordId);

    /**
     * 添加记录.
     *
     * @param recordFormDTO
     */
    void addRecordDetails(RecordFormDTO recordFormDTO);

    /**
     * 更新.
     *
     * @param list
     */
    void updateRecordDetail(List<RecordDetail> list,Boolean isAudit);

}