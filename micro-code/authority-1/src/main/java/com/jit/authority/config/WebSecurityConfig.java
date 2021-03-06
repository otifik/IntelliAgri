package com.jit.authority.config;

import com.jit.authority.jwtsecurity.bean.JwtProperty;
import com.jit.authority.jwtsecurity.handler.JwtAuthenticationEntryPoint;
import com.jit.authority.jwtsecurity.filter.JwtAuthenticationFilter;
import com.jit.authority.jwtsecurity.handler.LogoutSuccessHandler;
import com.jit.authority.jwtsecurity.util.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;//权限认证异常处理器

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Qualifier("userDetailServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtProperty jwtProperty; // jwt属性

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthHeaderFilter authHeaderFilter() {
        return new AuthHeaderFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 密码加密，这里采用了简单的MD5加密，可以根据需要自行配置
        return new CustomPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // 配置UserService和密码加密服务
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                /*
              这里对URL添加访问权限控制时需要注意：
              1. hasAuthority要以权限的全称标识，如ROLE_ADMIN，可以自定义权限标识
              2. hasRole要以ROLE_开头，且配置权限控制时要省略ROLE_前缀
              */
                //用户产品管理
                .antMatchers("/user/**").hasAnyRole("ADMIN","EXPERT","USER","BUSINESS")
                //产品管理
                .antMatchers("/product/business/**").hasAnyRole("ADMIN","BUSINESS")
                .antMatchers("/product/manager/**").hasAnyRole("ADMIN","EXPERT")
                .antMatchers("/product/visitor/**").permitAll()
                //订单管理
                .antMatchers("/order/business/**").hasAnyRole("ADMIN","BUSINESS")
                .antMatchers("/order/manager/**").hasAnyRole("ADMIN","EXPERT")
                .antMatchers("/order/consumer/**").hasAnyRole("ADMIN","EXPERT","USER")

                //获取养殖百科权限-所有人
                .antMatchers(HttpMethod.GET, "/ac-service/aquacu/**").permitAll()
                //养殖百科---增删改---管理员和专家
                .antMatchers(HttpMethod.POST, "/ac-service/aquacu/**").hasAnyRole("ADMIN", "EXPERT")
                .antMatchers(HttpMethod.PUT, "/ac-service/aquacu/**").hasAnyRole("ADMIN", "EXPERT")
                .antMatchers(HttpMethod.DELETE, "/ac-service/aquacu/**").hasAnyRole("ADMIN", "EXPERT")
                //日常投放和经济效益
                .antMatchers("/ac-service/**").permitAll()
                //物流系统
                .antMatchers("/micro-vehicles/**").permitAll()
                //file
                .antMatchers("/micro-vehicles/file/download/*").permitAll()
                .antMatchers("/file/download/zip/*").permitAll()

                .antMatchers("/user-center/**").permitAll()
                .anyRequest().fullyAuthenticated()
//                .and()
//                .formLogin().loginProcessingUrl("/auth/login").successHandler(loginSuccessHandler).failureHandler(loginFailureHandler)
                .and()
                .logout().logoutUrl("/auth/logout").logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
        // 配置jwt验证过滤器，位于用户名密码验证过滤器之后
        httpSecurity.addFilterBefore(new JwtAuthenticationFilter(jwtProperty, userDetailsService), UsernamePasswordAuthenticationFilter.class);
        // 禁用缓存
        httpSecurity.headers().cacheControl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        /* 在这里配置security放行的请求 */
//         统一静态资源
        web.ignoring().antMatchers("/**/*.gif", "/**/*.png", "/**/*.jpg", "/**/*.html", "/**/*.js", "/**/*.css", "/**/*.ico", "/webjars/**");
//        // Druid监控平台
//        web.ignoring().antMatchers("/druid/**");
        // swagger2
        web.ignoring().antMatchers("/swagger-ui.html*/**");
        web.ignoring().antMatchers("/**/swagger-ui.html*/**");
        web.ignoring().antMatchers("/ac-service/swagger-ui.html*/**");
        web.ignoring().mvcMatchers("/configuration/security",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/swagger-resources",
                "/swagger-ui.html",
                "/ac-service/swagger-ui.html",
                "/springfox-swagger-ui/*.js"
        );
        web.ignoring().mvcMatchers(
                "/**/configuration/security",
                "/**/swagger-resources/**",
                "/**/v2/api-docs",
                "/**/swagger-resources",
                "/**/swagger-ui.html",
                "/**/ac-service/swagger-ui.html",
                "/**/springfox-swagger-ui/*.js"
        );
    }
}
