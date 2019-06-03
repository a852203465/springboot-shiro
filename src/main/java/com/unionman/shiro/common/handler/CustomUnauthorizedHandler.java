package com.unionman.shiro.common.handler;

import com.unionman.shiro.common.enums.ResponseEnum;
import com.unionman.shiro.common.exception.CustomUnauthorizedException;
import com.unionman.shiro.common.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description: 权限异常控制处理器
 * @author Rong.Jia
 * @date 2019/04/17 09:37:22
 */
@Slf4j
@RestControllerAdvice
public class CustomUnauthorizedHandler {

    /**
     * @description: 捕捉所有Shiro异常
     * @param e 异常
     * @date 2019/04/17 09:46:22
     * @return ResponseVO
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public ResponseVO shiroHandle401(ShiroException e) {

        log.error("shiroHandle401 {}", e.getMessage());

        return ResponseVO.error(ResponseEnum.UNAUTHORIZED.getCode(), e.getMessage());

    }

    /**
     * @description: 单独捕捉Shiro(UnauthorizedException)异常
     * 该异常为访问有权限管控的请求而该用户没有所需权限所抛出的异常
     * @param e 未收授权异常
     * @date 2019/04/17 09:46:22
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResponseVO unauthorizedHandle401(UnauthorizedException e) {

        log.error("unauthorizedHandle401 {}", e.getMessage());

        return ResponseVO.error(ResponseEnum.SUBJECT_UNAUTHORIZED);

    }

    /**
     * @description: 单独捕捉Shiro(UnauthenticatedException)异常
     * 该异常为以游客身份访问有权限管控的请求无法对匿名主体进行授权，而授权失败所抛出的异常
     * @param e 未授权异常
     * @date 2019/04/17 09:46:22
     * @return ResponseVO 异常信息
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseBody
    public ResponseVO unauthenticatedHandle401(UnauthenticatedException e) {

        log.error("unauthenticatedHandle401 {}", e.getMessage());

        return ResponseVO.error(ResponseEnum.ANONYMOUS_SUBJECT_UNAUTHORIZED);

    }

    /**
     * @description: 捕捉UnauthorizedException自定义异常
     * @param e 自定义权限异常
     * @date 2019/04/17 09:46:22
     * @return 自定义异常信息
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomUnauthorizedException.class)
    @ResponseBody
    public ResponseVO customUnauthorizedHandle401(CustomUnauthorizedException e) {

        log.error("customUnauthorizedHandle401 {}", e.getMessage());

        return ResponseVO.error(e.getCode(), e.getMessage());

    }


}
