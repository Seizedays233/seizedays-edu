package com.seizedays.edu.common.handler;


import com.seizedays.edu.common.constants.ResultCodeEnum;
import com.seizedays.edu.common.exception.SeizedaysException;
import com.seizedays.edu.common.util.ExceptionUtil;
import com.seizedays.edu.common.vo.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//全局异常统一处理
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultMsg error(Exception e){
//        e.printStackTrace(); //输出异常堆栈信息
        log.error(ExceptionUtil.getMessage(e)); //输出异常堆栈信息 同时将异常日志记录到文件
        return ResultMsg.error();
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public ResultMsg error(BadSqlGrammarException e){
        log.error(ExceptionUtil.getMessage(e)); //输出异常堆栈信息
//        return ResultMsg.error().code(20003).message("sql语法错误");
        return ResultMsg.setResult(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResultMsg error(HttpMessageNotReadableException e){
        log.error(ExceptionUtil.getMessage(e)); //输出异常堆栈信息
        return ResultMsg.setResult(ResultCodeEnum.JSON_PARSE_ERROR);
    }

    //返回自定义异常
    @ExceptionHandler(SeizedaysException.class)
    @ResponseBody
    public ResultMsg error(SeizedaysException e){
        log.error(ExceptionUtil.getMessage(e)); //输出异常堆栈信息
        return ResultMsg.error().message(e.getMessage()).code(e.getCode());
    }


}
