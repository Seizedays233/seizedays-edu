package com.seizedays.edu.vod.controller;

import com.seizedays.edu.common.vo.ResultMsg;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"video"})
@CrossOrigin
@RestController
@RequestMapping("vod/video")
public class VideoController {

    @GetMapping
    public ResultMsg Test(){
        return ResultMsg.ok();
    }
}
