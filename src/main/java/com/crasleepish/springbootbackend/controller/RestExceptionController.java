package com.crasleepish.springbootbackend.controller;

import com.crasleepish.springbootbackend.bean.User;
import com.crasleepish.springbootbackend.util.exception.WhatTheFuckException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionController extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionController.class);

    /**
     *
     * @Valid校验器产生任何校验异常时，由该方法处理，返回一个包含所有异常信息的Json
     *
     * @param ex 捕获到的异常
     * @return ResponseEntity相当于ResponseBody + HttpHeader，泛型类型是响应Body的类型，一般用Object即可
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex.getMessage());
        Map<String, String> exs = ex.getFieldErrors().stream().collect(Collectors.toMap(
                e -> e.getField(),
                e -> e.getDefaultMessage(),
                (existingValue, newValue) -> existingValue + ";" + newValue)
        );
        return new ResponseEntity<Object>(exs, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * 输入参数到类型解析错误时，由该方法处理，对于其它参数解析异常，由父类中的默认方法处理
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex.getMessage());
        Map<String, String> exs = new HashMap<>();
        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException cause = (InvalidFormatException) ex.getCause();
            if (cause.getTargetType() == User.Gender.class) {
                exs.put("Gender", "不是合法的性别输入");
            }
            if (cause.getTargetType() == User.Type.class) {
                exs.put("Type", "不是合法的用户类型输入");
            }
            if (cause.getTargetType() == int.class || cause.getTargetType() == Integer.class) {
                exs.put("Integer", "不是合法的数字输入");
            }
            return new ResponseEntity<Object>(exs, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
        }
        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

    /**
     * 父类中没处理的其它异常，如自定义的异常，用@ExceptionHandler注解指定，由该函数处理
     * @param ex
     * @return
     */
    @ExceptionHandler({WhatTheFuckException.class})
    protected ResponseEntity<Object> handleInvalidFormat(WhatTheFuckException ex) {
        logger.error(ex.getMessage());
        Map<String, String> exs = new HashMap<>();
        exs.put("error", ex.getMessage());
        return new ResponseEntity<Object>(exs, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }
}
