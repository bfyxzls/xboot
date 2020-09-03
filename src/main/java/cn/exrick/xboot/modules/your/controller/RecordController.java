package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.*;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.base.entity.Department;
import cn.exrick.xboot.modules.base.entity.User;
import cn.exrick.xboot.modules.base.service.DepartmentService;
import cn.exrick.xboot.modules.base.service.UserService;
import cn.exrick.xboot.modules.base.utils.EntityUtil;
import cn.exrick.xboot.modules.your.dao.mapper.RecordMapper;
import cn.exrick.xboot.modules.your.entity.Court;
import cn.exrick.xboot.modules.your.entity.Record;
import cn.exrick.xboot.modules.your.entity.Task;
import cn.exrick.xboot.modules.your.entity.Type;
import cn.exrick.xboot.modules.your.service.CourtService;
import cn.exrick.xboot.modules.your.service.RecordService;
import cn.exrick.xboot.modules.your.service.TaskService;
import cn.exrick.xboot.modules.your.service.TypeService;
import cn.exrick.xboot.modules.your.util.FileUtil;
import cn.hutool.core.date.DateTime;
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
import java.util.*;

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
    RecordMapper recordMapper;
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
        record.setStatus(0);
        return getByCondition(false, record, searchVo, pageVo);

    }

    @Autowired
    UserService userService;

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
            }
            User user = userService.get(record1.getCreateBy());
            if (user != null) {
                record1.setCreateByName(user.getUsername());
                record1.setCreateByNickName(user.getNickName());

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

    @RequestMapping(value = "/v1/export", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取-导出")
    public void export(Record record,
                       SearchVo searchVo,
                       PageVo pageVo,
                       HttpServletResponse response) {
        pageVo.setSort("createTime");
        pageVo.setOrder("desc");
        pageVo.setPageSize(100000);
        record.setStatus(0);
        long t1 = System.currentTimeMillis();
        ExcelUtil.writeExcel(
                response,
                getByCondition(false, record, searchVo, pageVo).getResult().toList(),
                Record.class);
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
    }

    @RequestMapping(value = "/auditRecord", method = RequestMethod.POST)
    @ApiOperation(value = "批量审核任务")
    public Result<Object> auditRecord(@RequestParam String[] ids) {
        recordService.updateAuditStatus(Arrays.asList(ids));
        return ResultUtil.success("批量审核任务成功");
    }

    @RequestMapping("export")
    public void exportList(HttpServletResponse response, String departmentId, String courtId, String typeId,String id, SearchVo searchVo) throws Exception {
        String fileName = DateTime.now().year() + "" +
                DateTime.now().month() + "" +
                DateTime.now().dayOfMonth() + "" +
                DateTime.now().hour(true) + "" +
                DateTime.now().minute() + "" +
                DateTime.now().second() + "" +
                DateTime.now().millsecond() +
                ".xls";

        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(id)) {
            params.put("id", id);
        }
        if (StringUtils.isNotBlank(departmentId)) {
            params.put("departmentId", departmentId);
        }
        if (StringUtils.isNotBlank(courtId)) {
            params.put("courtId", courtId);
        }
        if (searchVo != null && StringUtils.isNotBlank(searchVo.getStartDate())) {
            params.put("startDate", searchVo.getStartDate());
        }
        if (searchVo != null && StringUtils.isNotBlank(searchVo.getEndDate())) {
            params.put("endDate", searchVo.getEndDate());
        }
        List<ExcelBean> ems = new ArrayList<>();
        if (typeId == null) {
            typeId = "1";
        }
        if (typeId.equals("1")) {
            reportYezhu(ems);
            ExcelMapUtil.export(response, fileName, ems, recordMapper.exportExcelYezhu(params));
        } else if (typeId.equals("2")) {
            reportZhuanjia(ems);
            ExcelMapUtil.export(response, fileName, ems, recordMapper.exportExcelZhuanjia(params));

        } else {
            reportGuanli(ems);
            ExcelMapUtil.export(response, fileName, ems, recordMapper.exportExcelGuanli(params));
        }

    }

    void commonField(List<ExcelBean> ems) {
        ems.add(new ExcelBean("评价编号", "record_id"));
        ems.add(new ExcelBean("创建人", "username"));
        ems.add(new ExcelBean("创建人昵称", "nick_name"));
        ems.add(new ExcelBean("创建时间", "create_time"));
        ems.add(new ExcelBean("行政区", "dept_title"));
        ems.add(new ExcelBean("小区", "court_title"));
        ems.add(new ExcelBean("项目类型", "project_type"));
        ems.add(new ExcelBean("评价类型", "type_title"));
        ems.add(new ExcelBean("任务名称", "task_title"));
        ems.add(new ExcelBean("审核状态", "status"));
        ems.add(new ExcelBean("纬度", "latitude"));
        ems.add(new ExcelBean("经度", "longitude"));
        ems.add(new ExcelBean("合计分数", "total"));
    }

    void reportYezhu(List<ExcelBean> ems) {
        commonField(ems);
        ems.add(new ExcelBean("时间", "时间"));
        ems.add(new ExcelBean("访问员姓名", "访问员姓名"));
        ems.add(new ExcelBean("居住小区名称", "居住小区名称"));
        ems.add(new ExcelBean("物业公司名称", "物业公司名称"));
        ems.add(new ExcelBean("居住楼栋编号", "居住楼栋编号"));
        ems.add(new ExcelBean("入住小区时间", "入住小区时间"));
        ems.add(new ExcelBean("身份", "身份"));
        ems.add(new ExcelBean("联系方式", "联系方式"));

        ems.add(new ExcelBean("1、物业工作人员（客服、保安、保洁等）服务态度及服务人员仪容仪表情况", "1、物业工作人员（客服、保安、保洁等）服务态度及服务人员仪容仪表情况"));
        ems.add(new ExcelBean("2、物业值班电话畅通情况", "2、物业值班电话畅通情况"));
        ems.add(new ExcelBean("3、公示物业服务标准、物业收费标准、物业企业和项目负责人信息情况", "3、公示物业服务标准、物业收费标准、物业企业和项目负责人信息情况"));
        ems.add(new ExcelBean("4、公示上一年物业费收支情况", "4、公示上一年物业费收支情况"));
        ems.add(new ExcelBean("5、装修管理情况", "5、装修管理情况"));
        ems.add(new ExcelBean("6、小区主要出入口保安值守情况", "6、小区主要出入口保安值守情况"));
        ems.add(new ExcelBean("7、小区内公共区域保安巡视情况", "7、小区内公共区域保安巡视情况"));
        ems.add(new ExcelBean("8、小区内车辆停放秩序管理情况", "8、小区内车辆停放秩序管理情况"));
        ems.add(new ExcelBean("9、小区房屋本体日常维修养护情况", "9、小区房屋本体日常维修养护情况"));
        ems.add(new ExcelBean("10、供水、供电设备运行、维修养护", "10、供水、供电设备运行、维修养护"));
        ems.add(new ExcelBean("11、电梯运行、维修养护", "11、电梯运行、维修养护"));
        ems.add(new ExcelBean("12、公共水、电报修服务", "12、公共水、电报修服务"));
        ems.add(new ExcelBean("13、公共照明完好程度", "13、公共照明完好程度"));
        ems.add(new ExcelBean("14、门禁及对讲设施维护情况", "14、门禁及对讲设施维护情况"));
        ems.add(new ExcelBean("15、楼道、楼梯间、电梯轿厢内卫生情况", "15、楼道、楼梯间、电梯轿厢内卫生情况"));
        ems.add(new ExcelBean("16、小区室外公共区域环境卫生情况", "16、小区室外公共区域环境卫生情况"));
        ems.add(new ExcelBean("17、生活垃圾及时清运情况", "17、生活垃圾及时清运情况"));
        ems.add(new ExcelBean("18、生活垃圾分类收集情况", "18、生活垃圾分类收集情况"));
        ems.add(new ExcelBean("19、公共区域绿化日常养护情况", "19、公共区域绿化日常养护情况"));
        ems.add(new ExcelBean("20、装修垃圾管理情况", "20、装修垃圾管理情况"));

        ems.add(new ExcelBean("1、物业工作人员（客服、保安、保洁等）服务态度及服务人员仪容仪表情况【问题说明】", "1、物业工作人员（客服、保安、保洁等）服务态度及服务人员仪容仪表情况【问题说明】"));
        ems.add(new ExcelBean("2、物业值班电话畅通情况【问题说明】", "2、物业值班电话畅通情况【问题说明】"));
        ems.add(new ExcelBean("3、公示物业服务标准、物业收费标准、物业企业和项目负责人信息情况【问题说明】", "3、公示物业服务标准、物业收费标准、物业企业和项目负责人信息情况【问题说明】"));
        ems.add(new ExcelBean("4、公示上一年物业费收支情况【问题说明】", "4、公示上一年物业费收支情况【问题说明】"));
        ems.add(new ExcelBean("5、装修管理情况【问题说明】", "5、装修管理情况【问题说明】"));
        ems.add(new ExcelBean("6、小区主要出入口保安值守情况【问题说明】", "6、小区主要出入口保安值守情况【问题说明】"));
        ems.add(new ExcelBean("7、小区内公共区域保安巡视情况【问题说明】", "7、小区内公共区域保安巡视情况【问题说明】"));
        ems.add(new ExcelBean("8、小区内车辆停放秩序管理情况【问题说明】", "8、小区内车辆停放秩序管理情况【问题说明】"));
        ems.add(new ExcelBean("9、小区房屋本体日常维修养护情况【问题说明】", "9、小区房屋本体日常维修养护情况【问题说明】"));
        ems.add(new ExcelBean("10、供水、供电设备运行、维修养护【问题说明】", "10、供水、供电设备运行、维修养护【问题说明】"));
        ems.add(new ExcelBean("11、电梯运行、维修养护【问题说明】", "11、电梯运行、维修养护【问题说明】"));
        ems.add(new ExcelBean("12、公共水、电报修服务【问题说明】", "12、公共水、电报修服务【问题说明】"));
        ems.add(new ExcelBean("13、公共照明完好程度【问题说明】", "13、公共照明完好程度【问题说明】"));
        ems.add(new ExcelBean("14、门禁及对讲设施维护情况【问题说明】", "14、门禁及对讲设施维护情况【问题说明】"));
        ems.add(new ExcelBean("15、楼道、楼梯间、电梯轿厢内卫生情况【问题说明】", "15、楼道、楼梯间、电梯轿厢内卫生情况【问题说明】"));
        ems.add(new ExcelBean("16、小区室外公共区域环境卫生情况【问题说明】", "16、小区室外公共区域环境卫生情况【问题说明】"));
        ems.add(new ExcelBean("17、生活垃圾及时清运情况【问题说明】", "17、生活垃圾及时清运情况【问题说明】"));
        ems.add(new ExcelBean("18、生活垃圾分类收集情况【问题说明】", "18、生活垃圾分类收集情况【问题说明】"));
        ems.add(new ExcelBean("19、公共区域绿化日常养护情况【问题说明】", "19、公共区域绿化日常养护情况【问题说明】"));
        ems.add(new ExcelBean("20、装修垃圾管理情况【问题说明】", "20、装修垃圾管理情况【问题说明】"));

        ems.add(new ExcelBean("1、物业工作人员（客服、保安、保洁等）服务态度及服务人员仪容仪表情况【问题说照片】", "1、物业工作人员（客服、保安、保洁等）服务态度及服务人员仪容仪表情况【问题照片】"));
        ems.add(new ExcelBean("2、物业值班电话畅通情况【问题说照片】", "2、物业值班电话畅通情况【问题照片】"));
        ems.add(new ExcelBean("3、公示物业服务标准、物业收费标准、物业企业和项目负责人信息情况【问题说照片】", "3、公示物业服务标准、物业收费标准、物业企业和项目负责人信息情况【问题照片】"));
        ems.add(new ExcelBean("4、公示上一年物业费收支情况【问题说照片】", "4、公示上一年物业费收支情况【问题照片】"));
        ems.add(new ExcelBean("5、装修管理情况【问题说照片】", "5、装修管理情况【问题照片】"));
        ems.add(new ExcelBean("6、小区主要出入口保安值守情况【问题说照片】", "6、小区主要出入口保安值守情况【问题照片】"));
        ems.add(new ExcelBean("7、小区内公共区域保安巡视情况【问题说照片】", "7、小区内公共区域保安巡视情况【问题照片】"));
        ems.add(new ExcelBean("8、小区内车辆停放秩序管理情况【问题说照片】", "8、小区内车辆停放秩序管理情况【问题照片】"));
        ems.add(new ExcelBean("9、小区房屋本体日常维修养护情况【问题说照片】", "9、小区房屋本体日常维修养护情况【问题照片】"));
        ems.add(new ExcelBean("10、供水、供电设备运行、维修养护【问题说照片】", "10、供水、供电设备运行、维修养护【问题照片】"));
        ems.add(new ExcelBean("11、电梯运行、维修养护【问题说照片】", "11、电梯运行、维修养护【问题照片】"));
        ems.add(new ExcelBean("12、公共水、电报修服务【问题说照片】", "12、公共水、电报修服务【问题照片】"));
        ems.add(new ExcelBean("13、公共照明完好程度【问题说照片】", "13、公共照明完好程度【问题照片】"));
        ems.add(new ExcelBean("14、门禁及对讲设施维护情况【问题说照片】", "14、门禁及对讲设施维护情况【问题照片】"));
        ems.add(new ExcelBean("15、楼道、楼梯间、电梯轿厢内卫生情况【问题说照片】", "15、楼道、楼梯间、电梯轿厢内卫生情况【问题照片】"));
        ems.add(new ExcelBean("16、小区室外公共区域环境卫生情况【问题说照片】", "16、小区室外公共区域环境卫生情况【问题照片】"));
        ems.add(new ExcelBean("17、生活垃圾及时清运情况【问题说照片】", "17、生活垃圾及时清运情况【问题照片】"));
        ems.add(new ExcelBean("18、生活垃圾分类收集情况【问题说照片】", "18、生活垃圾分类收集情况【问题照片】"));
        ems.add(new ExcelBean("19、公共区域绿化日常养护情况【问题说照片】", "19、公共区域绿化日常养护情况【问题照片】"));
        ems.add(new ExcelBean("20、装修垃圾管理情况【问题说照片】", "20、装修垃圾管理情况【问题照片】"));

    }

    void reportZhuanjia(List<ExcelBean> ems) {
        commonField(ems);
        ems.add(new ExcelBean("1、物业服务项目数量", "1、物业服务项目数量"));
        ems.add(new ExcelBean("2、物业服务项目面积", "2、物业服务项目面积"));
        ems.add(new ExcelBean("3、老旧小区物业服务", "3、老旧小区物业服务"));
        ems.add(new ExcelBean("4、公众责任保险或安全生产责任险参保情况", "4、公众责任保险或安全生产责任险参保情况"));
        ems.add(new ExcelBean("5、房屋及共用设施设备及共用设施设备运行、值 守和维护记录", "5、房屋及共用设施设备及共用设施设备运行、值 守和维护记录"));
        ems.add(new ExcelBean("6、业主装修档案管理情况", "6、业主装修档案管理情况"));
        ems.add(new ExcelBean("7、有限空间管理情况（台账、标识、协议签订、防 护设施）", "7、有限空间管理情况（台账、标识、协议签订、防 护设施）"));
        ems.add(new ExcelBean("8、生活垃圾收集运输服务合同签订情况", "8、生活垃圾收集运输服务合同签订情况"));
        ems.add(new ExcelBean("9、新生违建的上报记录", "9、新生违建的上报记录"));
        ems.add(new ExcelBean("10、生活垃圾分类管理情 况，垃圾桶配备及公示垃圾分类常识情况", "10、生活垃圾分类管理情 况，垃圾桶配备及公示垃圾分类常识情况"));
        ems.add(new ExcelBean("11、装修垃圾管理情况", "11、装修垃圾管理情况"));
        ems.add(new ExcelBean("12、公共区域保洁情况", "12、公共区域保洁情况"));
        ems.add(new ExcelBean("13、停车秩序管理情况", "13、停车秩序管理情况"));
        ems.add(new ExcelBean("14、绿化维护养护情况", "14、绿化维护养护情况"));
        ems.add(new ExcelBean("16、大件垃圾管理情况", "16、大件垃圾管理情况"));
        ems.add(new ExcelBean("17、电梯管理情况", "17、电梯管理情况"));
        ems.add(new ExcelBean("18、二次供水管理情况", "18、二次供水管理情况"));
        ems.add(new ExcelBean("19、消防设备设施管理情况", "19、消防设备设施管理情况"));
        ems.add(new ExcelBean("20、配电室管理情况", "20、配电室管理情况"));

        ems.add(new ExcelBean("1、物业服务项目数量【问题说明】", "1、物业服务项目数量【问题说明】"));
        ems.add(new ExcelBean("2、物业服务项目面积【问题说明】", "2、物业服务项目面积【问题说明】"));
        ems.add(new ExcelBean("3、老旧小区物业服务【问题说明】", "3、老旧小区物业服务【问题说明】"));
        ems.add(new ExcelBean("4、公众责任保险或安全生产责任险参保情况【问题说明】", "4、公众责任保险或安全生产责任险参保情况【问题说明】"));
        ems.add(new ExcelBean("5、房屋及共用设施设备及共用设施设备运行、值 守和维护记录【问题说明】", "5、房屋及共用设施设备及共用设施设备运行、值 守和维护记录【问题说明】"));
        ems.add(new ExcelBean("6、业主装修档案管理情况【问题说明】", "6、业主装修档案管理情况【问题说明】"));
        ems.add(new ExcelBean("7、有限空间管理情况（台账、标识、协议签订、防 护设施）【问题说明】", "7、有限空间管理情况（台账、标识、协议签订、防 护设施）【问题说明】"));
        ems.add(new ExcelBean("8、生活垃圾收集运输服务合同签订情况【问题说明】", "8、生活垃圾收集运输服务合同签订情况【问题说明】"));
        ems.add(new ExcelBean("9、新生违建的上报记录【问题说明】", "9、新生违建的上报记录【问题说明】"));
        ems.add(new ExcelBean("10、生活垃圾分类管理情 况，垃圾桶配备及公示垃圾分类常识情况【问题说明】", "10、生活垃圾分类管理情 况，垃圾桶配备及公示垃圾分类常识情况【问题说明】"));
        ems.add(new ExcelBean("11、装修垃圾管理情况【问题说明】", "11、装修垃圾管理情况【问题说明】"));
        ems.add(new ExcelBean("12、公共区域保洁情况【问题说明】", "12、公共区域保洁情况【问题说明】"));
        ems.add(new ExcelBean("13、停车秩序管理情况【问题说明】", "13、停车秩序管理情况【问题说明】"));
        ems.add(new ExcelBean("14、绿化维护养护情况【问题说明】", "14、绿化维护养护情况【问题说明】"));
        ems.add(new ExcelBean("16、大件垃圾管理情况【问题说明】", "16、大件垃圾管理情况【问题说明】"));
        ems.add(new ExcelBean("17、电梯管理情况【问题说明】", "17、电梯管理情况【问题说明】"));
        ems.add(new ExcelBean("18、二次供水管理情况【问题说明】", "18、二次供水管理情况【问题说明】"));
        ems.add(new ExcelBean("19、消防设备设施管理情况【问题说明】", "19、消防设备设施管理情况【问题说明】"));
        ems.add(new ExcelBean("20、配电室管理情况【问题说明】", "20、配电室管理情况【问题说明】"));

        ems.add(new ExcelBean("1、物业服务项目数量【问题说照片】", "1、物业服务项目数量【问题照片】"));
        ems.add(new ExcelBean("2、物业服务项目面积【问题说照片】", "2、物业服务项目面积【问题照片】"));
        ems.add(new ExcelBean("3、老旧小区物业服务【问题说照片】", "3、老旧小区物业服务【问题照片】"));
        ems.add(new ExcelBean("4、公众责任保险或安全生产责任险参保情况【问题说照片】", "4、公众责任保险或安全生产责任险参保情况【问题照片】"));
        ems.add(new ExcelBean("5、房屋及共用设施设备及共用设施设备运行、值 守和维护记录【问题说照片】", "5、房屋及共用设施设备及共用设施设备运行、值 守和维护记录【问题照片】"));
        ems.add(new ExcelBean("6、业主装修档案管理情况【问题说照片】", "6、业主装修档案管理情况【问题照片】"));
        ems.add(new ExcelBean("7、有限空间管理情况（台账、标识、协议签订、防 护设施）【问题说照片】", "7、有限空间管理情况（台账、标识、协议签订、防 护设施）【问题照片】"));
        ems.add(new ExcelBean("8、生活垃圾收集运输服务合同签订情况【问题说照片】", "8、生活垃圾收集运输服务合同签订情况【问题照片】"));
        ems.add(new ExcelBean("9、新生违建的上报记录【问题说照片】", "9、新生违建的上报记录【问题照片】"));
        ems.add(new ExcelBean("10、生活垃圾分类管理情 况，垃圾桶配备及公示垃圾分类常识情况【问题说照片】", "10、生活垃圾分类管理情 况，垃圾桶配备及公示垃圾分类常识情况【问题照片】"));
        ems.add(new ExcelBean("11、装修垃圾管理情况【问题说照片】", "11、装修垃圾管理情况【问题照片】"));
        ems.add(new ExcelBean("12、公共区域保洁情况【问题说照片】", "12、公共区域保洁情况【问题照片】"));
        ems.add(new ExcelBean("13、停车秩序管理情况【问题说照片】", "13、停车秩序管理情况【问题照片】"));
        ems.add(new ExcelBean("14、绿化维护养护情况【问题说照片】", "14、绿化维护养护情况【问题照片】"));
        ems.add(new ExcelBean("16、大件垃圾管理情况【问题说照片】", "16、大件垃圾管理情况【问题照片】"));
        ems.add(new ExcelBean("17、电梯管理情况【问题说照片】", "17、电梯管理情况【问题照片】"));
        ems.add(new ExcelBean("18、二次供水管理情况【问题说照片】", "18、二次供水管理情况【问题照片】"));
        ems.add(new ExcelBean("19、消防设备设施管理情况【问题说照片】", "19、消防设备设施管理情况【问题照片】"));
        ems.add(new ExcelBean("20、配电室管理情况【问题说照片】", "20、配电室管理情况【问题照片】"));
    }

    void reportGuanli(List<ExcelBean> ems) {
        commonField(ems);
        ems.add(new ExcelBean("接诉即办工作办理情况", "接诉即办工作办理情况"));
        ems.add(new ExcelBean("业主违法违规行为上报情况", "业主违法违规行为上报情况"));
        ems.add(new ExcelBean("履行安全生产责任情况", "履行安全生产责任情况"));
        ems.add(new ExcelBean("项目负责人社区报到情况", "项目负责人社区报到情况"));
        ems.add(new ExcelBean("重大活动保障情况", "重大活动保障情况"));
        ems.add(new ExcelBean("垃圾分类开展情况", "垃圾分类开展情况"));
        ems.add(new ExcelBean("其它专项治理情况", "其它专项治理情况"));
        ems.add(new ExcelBean("配合各管理部门开展各项工作情况", "配合各管理部门开展各项工作情况"));
        ems.add(new ExcelBean("配合党建引领改进物业管理工作情况", "配合党建引领改进物业管理工作情况"));
        ems.add(new ExcelBean("其他", "其他"));

        ems.add(new ExcelBean("接诉即办工作办理情况【问题说明】", "接诉即办工作办理情况【问题说明】"));
        ems.add(new ExcelBean("业主违法违规行为上报情况【问题说明】", "业主违法违规行为上报情况【问题说明】"));
        ems.add(new ExcelBean("履行安全生产责任情况【问题说明】", "履行安全生产责任情况【问题说明】"));
        ems.add(new ExcelBean("项目负责人社区报到情况【问题说明】", "项目负责人社区报到情况【问题说明】"));
        ems.add(new ExcelBean("重大活动保障情况【问题说明】", "重大活动保障情况【问题说明】"));
        ems.add(new ExcelBean("垃圾分类开展情况【问题说明】", "垃圾分类开展情况【问题说明】"));
        ems.add(new ExcelBean("其它专项治理情况【问题说明】", "其它专项治理情况【问题说明】"));
        ems.add(new ExcelBean("配合各管理部门开展各项工作情况【问题说明】", "配合各管理部门开展各项工作情况【问题说明】"));
        ems.add(new ExcelBean("配合党建引领改进物业管理工作情况【问题说明】", "配合党建引领改进物业管理工作情况【问题说明】"));
        ems.add(new ExcelBean("其他【问题说明】", "其他【问题说明】"));

        ems.add(new ExcelBean("接诉即办工作办理情况【问题说照片】", "接诉即办工作办理情况【问题照片】"));
        ems.add(new ExcelBean("业主违法违规行为上报情况【问题说照片】", "业主违法违规行为上报情况【问题照片】"));
        ems.add(new ExcelBean("履行安全生产责任情况【问题说照片】", "履行安全生产责任情况【问题照片】"));
        ems.add(new ExcelBean("项目负责人社区报到情况【问题说照片】", "项目负责人社区报到情况【问题照片】"));
        ems.add(new ExcelBean("重大活动保障情况【问题说照片】", "重大活动保障情况【问题照片】"));
        ems.add(new ExcelBean("垃圾分类开展情况【问题说照片】", "垃圾分类开展情况【问题照片】"));
        ems.add(new ExcelBean("其它专项治理情况【问题说照片】", "其它专项治理情况【问题照片】"));
        ems.add(new ExcelBean("配合各管理部门开展各项工作情况【问题说照片】", "配合各管理部门开展各项工作情况【问题照片】"));
        ems.add(new ExcelBean("配合党建引领改进物业管理工作情况【问题说照片】", "配合党建引领改进物业管理工作情况【问题照片】"));
        ems.add(new ExcelBean("其他【问题说照片】", "其他【问题照片】"));
    }
}
