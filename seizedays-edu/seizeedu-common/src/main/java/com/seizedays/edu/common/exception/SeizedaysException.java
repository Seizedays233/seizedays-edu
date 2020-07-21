package com.seizedays.edu.common.exception;

import com.seizedays.edu.common.constants.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "自定义全局异常")
public class SeizedaysException extends RuntimeException {

    @ApiModelProperty(value = "状态码")
    private Integer code;

    /**
     * 接收状态码和错误消息
     * @param code
     * @param message
     */
    public SeizedaysException(Integer code, String message){
        super(message);
        this.code = code;
    }


    public SeizedaysException(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "SeizedaysException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
