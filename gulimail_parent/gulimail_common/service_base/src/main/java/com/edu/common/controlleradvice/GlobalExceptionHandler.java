package com.edu.common.controlleradvice;

import com.edu.common.exception.GuliMailCodeMnum;
import com.edu.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: DDG
 * @Date: 2020/5/10 12:11
 * @Description:
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public R validExceptionHandle(Exception e){
        HashMap<String, String> exceptionMap = new HashMap<>();
        if (e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            BindingResult bindingResult = exception.getBindingResult();
            bindingResult.getFieldErrors()
                     .forEach((error) ->{
                         String field = error.getField();
                         String defaultMessage = error.getDefaultMessage();
                         exceptionMap.put(field, defaultMessage);
                     });
            return R.error(GuliMailCodeMnum.VALID_EXCEPTION.getCode(), GuliMailCodeMnum.VALID_EXCEPTION.getMessage()).put("data", exceptionMap);
        }
        log.error("出现了异常:{} , 出现的原因是{}", e.getClass().getSimpleName(), e.getMessage());
        return R.error(GuliMailCodeMnum.UN_NONE_EXCEPTION.getCode(), GuliMailCodeMnum.UN_NONE_EXCEPTION.getMessage()).put("data", e.getMessage());

    }

}
