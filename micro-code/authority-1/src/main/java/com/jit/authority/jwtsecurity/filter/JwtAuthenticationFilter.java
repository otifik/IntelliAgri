package com.jit.authority.jwtsecurity.filter;

import com.alibaba.fastjson.JSON;
import com.jit.authority.config.AppendHeaderRequestWrapper;
import com.jit.authority.domain.User;
import com.jit.authority.responseResult.ResultCode;
import com.jit.authority.jwtsecurity.bean.JwtProperty;
import com.jit.authority.jwtsecurity.util.JwtUtil;
import com.jit.authority.responseResult.exceptions.BusinessException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

/**
 * token 过滤器
 * create by zm
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtProperty jwtProperty;
    private UserDetailsService userDetailsService;

    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_HEAD = "Bearer ";
    private static final String HEADER_USER = "key_userinfo_in_http_header";

    public JwtAuthenticationFilter(JwtProperty jwtProperty, UserDetailsService userDetailsService) {
        this.jwtProperty = jwtProperty;
        this.userDetailsService = userDetailsService;
//        log.info("---------{}---------------",jwtProperty.toString());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.setContentType("application/json");
        String authorization = httpServletRequest.getHeader(TOKEN_HEADER);
//        List<String> pass =  Arrays.asList("/auth/signup","/auth/login","/auth/logout");

//
        if (!StringUtils.isEmpty(authorization) && !authorization.startsWith(TOKEN_HEAD)) { // Token格式错误
            httpServletResponse.getWriter().write(JSON.toJSONString(new BusinessException(ResultCode.TOKEN_FORMAT_ERROR)));
            return;
        }
        if (!StringUtils.isEmpty(authorization) && authorization.startsWith(TOKEN_HEAD)) {
            Claims claims = JwtUtil.parseToken(authorization, jwtProperty.getBase64Security());

            if (null == claims) { // Token不可解码
                httpServletResponse.getWriter().write(JSON.toJSONString(new BusinessException(ResultCode.TOKEN_FORMAT_ERROR)));
                return;
            }
            if (claims.getExpiration().getTime() < new Date().getTime()) { // Token超时
                httpServletResponse.getWriter().write(JSON.toJSONString(new BusinessException(ResultCode.TOKEN_EXPIRED)));
                return;
            }
            String username = JwtUtil.getUsernameFromToken(authorization, jwtProperty);
            if (!StringUtils.isEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                User user = User.of();
                if (JwtUtil.validateToken(authorization, userDetails, jwtProperty)) {
                    UsernamePasswordAuthenticationToken authentication
                            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    AppendHeaderRequestWrapper requestWrapper = new AppendHeaderRequestWrapper(httpServletRequest);
                    user.setUsername(userDetails.getUsername());
                    String userJson = JSON.toJSONString(user);
                    requestWrapper.putHeader(HEADER_USER, userJson);

                }
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
