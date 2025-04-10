package com.itheima.ds.exception;

import com.itheima.ds.common.result.CodeMsg;
import com.itheima.ds.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(GlobalException.class)
    public Result<String> handleGlobalException(GlobalException e) {
        log.error("全局异常: {}", e.getMessage(), e);
        return Result.error(e.getCodeMsg());
    }

    /**
     * 处理参数校验异常（@Valid注解校验）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数校验异常: {}", e.getMessage(), e);
        ObjectError error = e.getBindingResult().getAllErrors().get(0);
        return Result.error(CodeMsg.PARAMETER_ERROR.fillArgs(error.getDefaultMessage()));
    }

    /**
     * 处理参数绑定异常
     */
    @ExceptionHandler(BindException.class)
    public Result<String> handleBindException(BindException e) {
        log.error("参数绑定异常: {}", e.getMessage(), e);
        List<ObjectError> errors = e.getAllErrors();
        ObjectError error = errors.get(0);
        return Result.error(CodeMsg.PARAMETER_ERROR.fillArgs(error.getDefaultMessage()));
    }

    /**
     * 处理参数校验异常（@Validated注解校验）
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<String> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("参数校验异常: {}", e.getMessage(), e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        return Result.error(CodeMsg.PARAMETER_ERROR.fillArgs(violation.getMessage()));
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error("系统异常: {}", e.getMessage(), e);
        return Result.error(CodeMsg.SERVER_ERROR);
    }
} 