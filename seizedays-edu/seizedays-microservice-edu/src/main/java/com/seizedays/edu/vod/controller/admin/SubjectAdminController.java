package com.seizedays.edu.vod.controller.admin;


import com.seizedays.edu.common.constants.ResultCodeEnum;
import com.seizedays.edu.common.exception.SeizedaysException;
import com.seizedays.edu.common.vo.ResultMsg;
import com.seizedays.edu.service.SubjectService;
import com.seizedays.edu.vo.SubjectNestedVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin/edu/subject")
@CrossOrigin
@Api(tags = {"subject"})
@Slf4j
public class SubjectAdminController {

    @Autowired
    private SubjectService subjectService;

    @ApiOperation(value = "Excel批量导入")
    @PostMapping("/import")
    public ResultMsg batchImport(
            @ApiParam(name = "file", value = "Excel文件", required = true)
            @RequestParam("file") MultipartFile file) {

        try {

            List<String> errorMsg = subjectService.batchImport(file);
            if (errorMsg.size() == 0){
                return ResultMsg.ok().message("批量数据导入成功");
            }else {
                return ResultMsg.error().message("部分数据导入失败").data("dataMsgList", errorMsg);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new SeizedaysException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }

    }

    @ApiOperation(value = "嵌套数据列表")
    @GetMapping("")
    public ResultMsg nestedList(){
        List<SubjectNestedVO> subjectNestedVoList = subjectService.nestedList();
        return ResultMsg.ok().data("items", subjectNestedVoList);
    }
}
