package com.jit.authority.config;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.jit.authority.jwtsecurity.bean.JwtProperty;
import com.jit.authority.jwtsecurity.util.JwtUtil;
import com.jit.authority.responseResult.ResultCode;
import com.jit.authority.responseResult.exceptions.BusinessException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 调用服务前添加认证请求头过滤器
 *
 * @author
 **/

public class AuthHeaderFilter extends ZuulFilter {
    @Autowired
    private JwtProperty jwtProperty;
//    @Autowired
//    private UserDetailsService userDetailsService;
    private static final String HEADER_USER = "key_userinfo_in_http_header";

    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_HEAD = "Bearer ";



    @Override
    public boolean shouldFilter() {
//		RequestContext ctx = RequestContext.getCurrentContext();
//		Object success = ctx.get("isSuccess");
//		return success == null ? true : Boolean.parseBoolean(success.toString());
        return true;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public Object run() {


        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpServletResponse response = currentContext.getResponse();
        // 获取出请求头
        String header = request.getHeader(TOKEN_HEADER);

//		// 放行登录请求
//		if (request.getRequestURL().indexOf("login") > 0) {
//			return null;
//		}
        String authorization = header;
        String username = JwtUtil.getUsernameFromToken(authorization, jwtProperty);
        if (!StringUtils.isEmpty(header)) {
            if (header.startsWith("Bearer ")) {
                String token = header.substring(7);
                if (!StringUtils.isEmpty(token)) {
                    System.out.println("token==" + token);
                    System.out.println("username==" + username);
                    currentContext.addZuulRequestHeader(HEADER_USER, username);
//		}
                } else {
                    try {
                        response.getWriter().write(JSON.toJSONString(new BusinessException(ResultCode.TOKEN_FORMAT_ERROR)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return null;
    }
}