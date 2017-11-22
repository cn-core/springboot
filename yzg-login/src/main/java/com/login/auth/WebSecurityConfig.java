package com.login.auth;

import com.common.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;


/**
 * @author yangzhiguo
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final int TOKEN_VALIDITY_SECONDS = 60;

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // 允许所有用户访问的
                .antMatchers(
                        "/*",
                        "/test/*"
                ).permitAll()
                // 其他地址的访问均需验证权限
                .anyRequest()
                .authenticated()
                .and()
                // 指定登录页
                .formLogin()
                // .loginPage("/auth")
                .defaultSuccessUrl("/success")
                .permitAll()
                // 登录成功后可使用loginSuccessHandler()存储用户信息,可选
                .successHandler(loginSuccessHandler())
                .and()
                .logout()
                .logoutSuccessUrl("/home")
                // 会话失效
                .invalidateHttpSession(true)
                .and()
                // 登录后记住用户,下次自动登录
                // 数据库中必须存在名为persistent_logins的表
                .rememberMe()
                // 指定记住登录信息所使用的的数据源
                .tokenRepository(tokenRepository())
                .tokenValiditySeconds(TOKEN_VALIDITY_SECONDS)
                // 防止表单重复提交
                .and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 指定密码加密所使用的的密码器为passwordEncoder()
        // 需要将密码加密后写入数据库
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
        // 不删除凭证,以便记住用户名
        auth.eraseCredentials(false);
    }

    /**
     * 密码编译器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Utils.encodeMD5((String) rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(MD5Utils.encodeMD5((String) rawPassword));
            }
        };
    }

    /**
     * 令牌库
     */
    @Bean
    public JdbcTokenRepositoryImpl tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }
}
