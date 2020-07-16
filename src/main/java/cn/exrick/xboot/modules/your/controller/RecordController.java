package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.ExcelUtil;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.base.entity.Department;
import cn.exrick.xboot.modules.base.service.DepartmentService;
import cn.exrick.xboot.modules.base.utils.EntityUtil;
import cn.exrick.xboot.modules.your.entity.Court;
import cn.exrick.xboot.modules.your.entity.Record;
import cn.exrick.xboot.modules.your.entity.Task;
import cn.exrick.xboot.modules.your.entity.Type;
import cn.exrick.xboot.modules.your.service.CourtService;
import cn.exrick.xboot.modules.your.service.RecordService;
import cn.exrick.xboot.modules.your.service.TaskService;
import cn.exrick.xboot.modules.your.service.TypeService;
import cn.exrick.xboot.modules.your.util.FileUtil;
import io.micrometer.core.instrument.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lind
 */
@Slf4j
@RestController
@Api(description = "评价-评价记录")
@RequestMapping("/xboot/record")
@Transactional
public class RecordController extends XbootBaseController<Record, String> {

    @Autowired
    FileUtil fileUtil;
    @Autowired
    EntityUtil entityUtil;
    @Autowired
    private RecordService recordService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private CourtService courtService;
    @Autowired
    private DepartmentService departmentService;

    @Override
    public RecordService getService() {
        return recordService;
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取-只查自己的")
    public Result<Page<Record>> getByCondition(Record record,
                                               SearchVo searchVo,
                                               PageVo pageVo) {
        return getByCondition(true, record, searchVo, pageVo);
    }

    @RequestMapping(value = "/getByConditionMgr", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取-按权限查询")
    public Result<Page<Record>> getByConditionMgr(Record record,
                                                  SearchVo searchVo,
                                                  PageVo pageVo) {
        pageVo.setSort("createTime");
        pageVo.setOrder("desc");
        return getByCondition(false, record, searchVo, pageVo);

    }


    public Result<Page<Record>> getByCondition(Boolean isSelf,
                                               Record record,
                                               SearchVo searchVo,
                                               PageVo pageVo) {
        pageVo.setSort("createTime");
        pageVo.setOrder("desc");
        Page<Record> page = recordService.findByCondition(isSelf, record, searchVo, PageUtil.initPage(pageVo));

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
                record1.setCourtTitle(court.getTitle());
                record1.setDepartmentId(court.getDepartmentId());//从新设置小区的行政区
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


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加记录")
    public Result<Object> add(Record entity) {
        entityUtil.initEntity(entity);
        recordService.save(entity);
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "编辑记录")
    public Result<Object> edit(Record entity, @RequestParam String id) {
        Record old = recordService.get(id);
        old.setTaskId(entity.getTaskId());
        old.setTypeId(entity.getTypeId());
        old.setCourtId(entity.getCourtId());
        old.setScore(entity.getScore());
        recordService.save(old);
        return ResultUtil.success("保存成功");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除记录")
    public Result<Object> delByIds(@RequestParam String[] ids) {
        for (String id : ids) {
            recordService.delete(id);
        }
        return ResultUtil.success("删除成功");
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取-导出")
    public void export(Record record,
                       SearchVo searchVo,
                       PageVo pageVo,
                       HttpServletResponse response) {
        pageVo.setSort("createTime");
        pageVo.setOrder("desc");
        pageVo.setPageSize(100000);
        long t1 = System.currentTimeMillis();
        ExcelUtil.writeExcel(
                response,
                getByCondition(false, record, searchVo, pageVo).getResult().toList(),
                Record.class);
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
    }
}
