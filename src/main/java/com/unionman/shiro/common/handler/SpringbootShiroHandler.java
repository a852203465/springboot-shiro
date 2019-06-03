package com.unionman.shiro.common.handler;

import com.unionman.shiro.common.enums.ResponseEnum;
import com.unionman.shiro.common.exception.SpringbootShiroException;
import com.unionman.shiro.common.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.crypto.BadPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.LinkedList;
import java.util.List;

/**
 * @description: 异常控制处理器
 * @author Rong.Jia
 * @date 2019/4/3
 */
@Slf4j
@RestControllerAdvice
public class SpringbootShiroHandler {

    /**
     * @description: 捕获自定义异常，并返回异常数据
     * @author Rong.Jia
     * @date 2019/4/3 8:46
     */
    @ExceptionHandler(value = SpringbootShiroException.class)
    public ResponseVO springbootShiroExceptionHandler(SpringbootShiroException e){

        log.error("springbootShiroExceptionHandler  {}", e.getMessage());

        return ResponseVO.error(e.getCode(), e.getMessage());

    }

    /**
     * @description: 捕捉404异常
     * @param e 404 异常
     * @date 2019/04/17 09:46:22
     * @return
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseVO noHandlerFoundHandle(NoHandlerFoundException e) {

        log.error("noHandlerFoundHandle {}", e.getMessage());

        return new ResponseVO(ResponseEnum.NOT_FOUND);

    }

    /**
     *
     * @description:  httpserver 错误异常类, 捕获http请求异常
     * @author: Rong.Jia
     * @date: 2018年4月23日 上午10:05:40
     */
    @ExceptionHandler(value = HttpClientErrorException.class)
    @ResponseBody
    public ResponseVO handler(HttpClientErrorException e) {

        log.error("HttpClientErrorException : {}",e.getMessage());

        //   HttpServerErrorException httpServerErrorException = (HttpServerErrorException)e;
        String statusCode = e.getStatusCode().toString();
        //noinspection AlibabaUndefineMagicConstant
        if (StringUtils.contains(statusCode, HttpStatus.INTERNAL_SERVER_ERROR.value())) {
            return ResponseVO.error(ResponseEnum.SYSTEM_ERROR);
        } else if (StringUtils.contains(statusCode, HttpStatus.NOT_FOUND.value())) {
            return ResponseVO.error(ResponseEnum.INT404_NOT_FOUND);
        } else if (StringUtils.contains(statusCode, HttpStatus.BAD_REQUEST.value())) {
            return ResponseVO.error(ResponseEnum.INT400_BAD_REQUEST);
        }
        return ResponseVO.error(ResponseEnum.SYSTEM_ERROR.getCode(),"未知错误，请联系系统管理员处理");
    }

    /**
     * @description: 字段验证异常处理
     * @param e  异常信息
     * @author Rong.Jia
     * @date 2018/8/4 20:02
     * @return ResponseVO 返回异常信息
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseVO handle(MethodArgumentNotValidException e){
        List<String> errorList = new LinkedList<>();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        for (ObjectError error : errors ) {

            log.error ("MethodArgumentNotValidException : {} - {}",error.getObjectName(),error.getDefaultMessage());
            errorList.add(error.getDefaultMessage());

        }

        log.error("MethodArgumentNotValidExceptionHandle :{}",e.getMessage());

        return ResponseVO.error(ResponseEnum.PARAMETER_ERROR.getCode(),errorList.get(0));

    }

    /**
     * @description: 不支持当前请求方法
     * @param e  异常信息
     * @author Rong.Jia
     * @date 2018/8/4 20:02
     * @return ResponseVO 返回异常信息
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseVO handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {

        log.error("HttpRequestMethodNotSupportedException : {}",e.getMessage());

        return ResponseVO.error(ResponseEnum.REQUEST_MODE_ERROR);

    }

    /**
     * @description: 不支持当前媒体类型
     * @param e  异常信息
     * @author Rong.Jia
     * @date 2018/8/4 20:02
     * @return ResponseVO 返回异常信息
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseVO handleHttpMediaTypeNotSupportedException(Exception e) {

        log.error("HttpMediaTypeNotSupportedException : {}",e.getMessage());

        return ResponseVO.error(ResponseEnum.MEDIA_TYPE_NOT_SUPPORTED);

    }

    /**
     * @description: 默认异常处理
     * @param e  异常信息
     * @author Rong.Jia
     * @date 2018/8/4 20:02
     * @return ResponseVO 返回异常信息
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseVO defaultErrorHandler( Exception e) {

        if (e.getCause() instanceof SpringbootShiroException) {
            springbootShiroExceptionHandler((SpringbootShiroException)e);
        }

        log.error("defaultErrorHandler : {}",e.getMessage());

        return ResponseVO.error(ResponseEnum.SYSTEM_ERROR);
    }

    /**
     * @description: 默认运行异常处理
     * @param e  异常信息
     * @author Rong.Jia
     * @date 2018/8/4 20:02
     * @return ResponseVO 返回异常信息
     */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ResponseVO runtimeErrorHandler(RuntimeException e) {

        if (e.getCause() instanceof SpringbootShiroException) {
            springbootShiroExceptionHandler((SpringbootShiroException)e);
        }

        if(e.getCause() instanceof HttpMessageNotReadableException){
            httpMesssageHandler((HttpMessageNotReadableException)e.getCause());
        }

        if (e.getCause() instanceof MultipartException) {
            multipartExceptionHandler((MultipartException)e.getCause());
        }

        log.error("runtimeErrorHandler : {}", e);

        return ResponseVO.error(ResponseEnum.SYSTEM_ERROR);
    }

    /**
     * @description: 文件上传异常处理
     * @param e  异常信息
     * @author Rong.Jia
     * @date 2018/8/4 20:02
     * @return ResponseVO 返回异常信息
     */
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    @ResponseBody
    public ResponseVO maxUploadHandler(MaxUploadSizeExceededException e) {

        log.error("MaxUploadSizeExceededException : {}", e.getMessage());

        return ResponseVO.error(ResponseEnum.FILE_LIMIT_EXCEEDED);
    }

    /**
     * @description: 使用反射或者代理造成的异常需要根据异常类型单独处理
     * @param e  异常信息
     * @author Rong.Jia
     * @date 2018/8/4 20:02
     * @return ResponseVO 返回异常信息
     */
    @ExceptionHandler(value = UndeclaredThrowableException.class)
    @ResponseBody
    public ResponseVO undeclaredThrowableException(HttpServletRequest req, UndeclaredThrowableException e) {

        //密文解密失败异常
        if (e.getCause() instanceof BadPaddingException) {

            log.error("BadPaddingException : {}",e.getMessage());
            return ResponseVO.error(ResponseEnum.SYSTEM_ERROR);
        }

        log.error("UndeclaredThrowableException : {}",e.getMessage());
        return ResponseVO.error(ResponseEnum.SYSTEM_ERROR);
    }

    /**
     * @description: http 请求参数格式错误
     * @param e  异常信息
     * @author Rong.Jia
     * @date 2018/8/4 20:02
     * @return ResponseVO 返回异常信息
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseVO  httpMesssageHandler(HttpMessageNotReadableException e){

        log.error("HttpMessageNotReadableException : {}",e.getMessage());

        return ResponseVO.error(ResponseEnum.REQUEST_PARAMETER_FORMAT_IS_INCORRECT);
    }

    /**
     * @description: 超出最大限制捕获异常
     * @param e 异常信息
     * @author Rong.Jia
     * @date 2018/8/4 20:02
     * @return ResponseVO 返回异常信息
     */
    @ExceptionHandler(value = MultipartException.class)
    @ResponseBody
    public ResponseVO  multipartExceptionHandler(MultipartException e){

        log.error("multipartExceptionHandler : {}",e.getMessage());

        return ResponseVO.error(ResponseEnum.FILE_LIMIT_EXCEEDED);

    }



}
