package com.example.springtest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .antMatchers("/", "/members/new").permitAll() // home은 아무나 들어갈 수 있음
                    .anyRequest().authenticated() // 다른 곳은 권한이 있어야 들어갈 수 있다
                    .and()
                .formLogin()
                    .loginPage("/account/login") // 로그인페이지 권한 필요없음
                    .permitAll()
                    .and()
                .logout() // 로그아웃 페이지도
                    .permitAll();
    }

    @Autowired
    public void configurationGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select username, password, enabled "
                    + "from Member "
                    + "where username = ?")
                .authoritiesByUsernameQuery("select m.username, r.name "
                        + "from member_role mr inner join Member m on mr.member_id = m.id "
                        + "inner join role r on mr.role_id = r.id "
                        + "where m.username = ?");
    }

    @Bean
    static public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Authentication - 로그인
    //Authorization - 권한


}