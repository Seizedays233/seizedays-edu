package com.seizedays.edu.oss.controllers;

import com.seizedays.edu.common.vo.ResultMsg;
import com.seizedays.edu.oss.services.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = {"file"})
@CrossOrigin
@RestController
@RequestMapping("/admin/oss/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public ResultMsg uploadFile(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file){

        String uploadUrl = fileService.uploadFile(file);

        return ResultMsg.ok().message("文件上传成功").data("url", uploadUrl);
    }

}
