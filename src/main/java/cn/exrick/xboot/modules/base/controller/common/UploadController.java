package cn.exrick.xboot.modules.base.controller.common;

import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.your.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * @author Exrickx
 */
@Slf4j
@RestController
@Api(description = "文件上传接口")
@RequestMapping("/xboot/upload")
@Transactional
public class UploadController {

    @Autowired
    private FileUtil fileUtil;

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @ApiOperation(value = "文件上传")
    public Result<Object> upload(@RequestParam(required = false) MultipartFile file) {

        String result = null;
        String fileName = fileUtil.renamePic(file.getOriginalFilename());
        try {
            result = fileUtil.localUpload(file, fileName);
        } catch (Exception e) {
            log.error(e.toString());
            return ResultUtil.error(e.toString());
        }
        return ResultUtil.data(result);
    }

    @RequestMapping(value = "/pic/{path}", method = RequestMethod.GET)
    @ApiOperation(value = "显示照片")
    public void pic(@PathVariable String path, HttpServletResponse response) {
        fileUtil.view(path, response);
    }

}
