package com.ls.mall.Interceptor;

import com.ls.mall.constants.MallConst;
import com.ls.mall.exception.UserLoginException;
import com.ls.mall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @className: UserLoginInterceptor
 * @description: 拦截器
 * @author: liusCoding
 * @create: 2020-02-16 15:53
 */
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {


    /**
     * true 表示继续流程，false表示中断
     *
     * @param request
     * @param response
     * @param handler
     * @date: 2020/2/16
     * @return: boolean
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("preHandle.....");
        User user = (User) request.getSession().getAttribute(MallConst.CURRENT_USER);
        if (Objects.isNull(user)) {
            log.info("user=null");
            throw new UserLoginException();
        }

        return true;
    }
}
