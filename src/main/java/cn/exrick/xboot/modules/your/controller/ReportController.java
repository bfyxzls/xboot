package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.base.entity.Department;
import cn.exrick.xboot.modules.base.service.DepartmentService;
import cn.exrick.xboot.modules.your.entity.Court;
import cn.exrick.xboot.modules.your.entity.Record;
import cn.exrick.xboot.modules.your.entity.Task;
import cn.exrick.xboot.modules.your.entity.Type;
import cn.exrick.xboot.modules.your.service.CourtService;
import cn.exrick.xboot.modules.your.service.RecordService;
import cn.exrick.xboot.modules.your.service.TaskService;
import cn.exrick.xboot.modules.your.service.TypeService;
import io.micrometer.core.instrument.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lind
 */
@Slf4j
@RestController
@Api(description = "评价-评价报告")
@RequestMapping("/xboot/report")
@Transactional
public class ReportController extends XbootBaseController<Record, String> {

    @Autowired
    DepartmentService departmentService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private CourtService courtService;

    @Override
    public RecordService getService() {
        return recordService;
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<Record>> getByCondition(Record record,
                                               SearchVo searchVo,
                                               PageVo pageVo) {
        record.setStatus(1);//已审核
        Page<Record> page = recordService.findByCondition(record, searchVo, PageUtil.initPage(pageVo));

        for (Record record1 : page) {
            Task task = taskService.get(record1.getTaskId());
            if (task != null) {
                record1.setTaskTitle(taskService.get(record1.getTaskId()).getTitle());
            }
            Type type = typeService.get(record1.getTypeId());
            if (type != null) {
                record1.setTypeTitle(type.getTitle());
            }
            Court court = courtService.get(record1.getCourtId());
            if (court != null) {
                record1.setCourt(court);
                record1.setCourtTitle(court.getTitle());
            }
            if (StringUtils.isNotBlank(record1.getDepartmentId())) {
                Department department = departmentService.get(record1.getDepartmentId());
                departmentService.generateParents(department);
                record1.setDepartment(department);
                List<String> result = new ArrayList<>();
                departmentService.generateParentTitle(department, result);
                record1.setDepartmentTreeTitle(String.join("-", result));
            }
        }

        return new ResultUtil<Page<Record>>().setData(page);
    }


}
