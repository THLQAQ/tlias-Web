package com.yun.filter;

import com.alibaba.fastjson.JSONObject;
import com.yun.pojo.Result;
import com.yun.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //获取请求的url
        String url = req.getRequestURL().toString();
        log.info("请求的url:{}",url);

        //判断请求url中是否包含login
        if (url.contains("login")) {
            log.info("登录操作，放行");
            chain.doFilter(request, response);
            return;
        }

        //获取请求头中的令牌
        String jwt = req.getHeader("token");

        if (!StringUtils.hasLength(jwt)) {
            log.info("请求头token为空");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return;
        }
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("解析令牌失败，返回未登录的错误信息");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return;
        }
        log.info("令牌合法，放行");
        chain.doFilter(request, response);
    }
}
