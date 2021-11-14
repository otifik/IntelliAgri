package com.jit.aquaculture.config;//package com.jit.aquaculture.config;

import com.jit.aquaculture.domain.User;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Component
public class TransmitUserInfoFilter implements Filter {
    private static final String HEADER_USER = "key_userinfo_in_http_header";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, IOException {
        this.initUserInfo((HttpServletRequest) request);
        chain.doFilter(request, response);
    }

    private void initUserInfo(HttpServletRequest request) {

        String username = request.getHeader(HEADER_USER);

        if (StringUtils.isNotBlank(username)) {
            System.out.println("User Header=====" + username);
            User userInfo = User.of();
            //将UserInfo放入上下文中
            userInfo.setUsername(username);
            UserInfoContext.setUser(userInfo);
        } else {
            return;
        }
    }

    @Override
    public void destroy() {
    }

}