package com.sy.exception;


import com.sy.util.Result;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @ClassName GlobalExceptionHandler
 * @Description TODO
 * @Author Administrator
 * @Date: 2021/12/20 13:47
 * @Version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public Result respError(Exception e) {
        Result result = new Result();
        if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
            result.setCode(1);
        } else {
            //写日志
            LOGGER.error("业务异常", e);
            result.setCode(Result.CODE_ERROR);
        }
        result.setMsg(Result.MSG_ERROR);
        return result;
    }
}
