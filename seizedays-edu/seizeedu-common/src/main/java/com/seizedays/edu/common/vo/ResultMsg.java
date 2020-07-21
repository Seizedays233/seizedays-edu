package com.seizedays.edu.common.vo;

import com.seizedays.edu.common.constants.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@ApiModel(value = "全局统一返回结果")
public class ResultMsg {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    private ResultMsg(){}

    public static ResultMsg ok(){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        resultMsg.setCode(ResultCodeEnum.SUCCESS.getCode());
        resultMsg.setMessage(ResultCodeEnum.SUCCESS.getMessage());

        return resultMsg;
    }

    public static ResultMsg error(){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setSuccess(ResultCodeEnum.UNKNOWN_REASON.getSuccess());
        resultMsg.setCode(ResultCodeEnum.UNKNOWN_REASON.getCode());
        resultMsg.setMessage(ResultCodeEnum.UNKNOWN_REASON.getMessage());

        return resultMsg;
    }

    public ResultMsg data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

    public ResultMsg data(String key, Object value){
        this.data.put(key, value);
        return  this;
    }

    public ResultMsg message(String message){
        this.setMessage(message);
        return this;
    }

    public ResultMsg code(Integer code){
        this.setCode(code);
        return this;
    }

    public ResultMsg success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public static ResultMsg setResult(ResultCodeEnum resultCodeEnum){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setSuccess(resultCodeEnum.getSuccess());
        resultMsg.setCode(resultCodeEnum.getCode());
        resultMsg.setMessage(resultCodeEnum.getMessage());
        return resultMsg;
    }

}
