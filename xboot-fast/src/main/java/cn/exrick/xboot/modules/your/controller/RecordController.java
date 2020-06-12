package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.entity.Record;
import cn.exrick.xboot.modules.your.service.*;
import cn.exrick.xboot.modules.your.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

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
    private RecordService recordService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TenementService tenementService;
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

        Page<Record> page = recordService.findByCondition(record, searchVo, PageUtil.initPage(pageVo));

        for (Record record1 : page) {
            record1.setTaskTitle(taskService.get(record1.getTaskId()).getTitle());
            record1.setTypeTitle(typeService.get(record1.getTypeId()).getTitle());
            record1.setCourtTitle(courtService.get(record1.getCourtId()).getTitle());
            record1.setTenementTitle(tenementService.get(record1.getTenementId()).getTitle());

        }

        return new ResultUtil<Page<Record>>().setData(page);
    }

    @RequestMapping(value = "/upload/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "上传照片")
    public Result<Object> upload(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        String fileName = FileUtil.renamePic(file.getOriginalFilename());
        Record old = recordService.get(id);
        old.setPictureUrl(fileName);
        recordService.save(old);
        return ResultUtil.data(fileUtil.localUpload(file, fileName));
    }

    @RequestMapping(value = "/pic/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "显示照片")
    public void pic(@PathVariable String id, HttpServletResponse response) {
        String path = recordService.get(id).getPictureUrl();
        fileUtil.view(path, response);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加记录")
    public Result<Object> add(Record entity) {
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
        old.setCreateDepartmentId(entity.getCreateDepartmentId());
        old.setScore(entity.getScore());
        old.setTenementId(entity.getTenementId());
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
}
