package com.learnspring.courseUsers.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnspring.courseUsers.service.MongoUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MongoUserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Configure security.");
        http
            .authorizeRequests()
            .antMatchers("/", "/courses").permitAll()
            .anyRequest().authenticated()
                .and()
                    .formLogin().permitAll()
//                    .loginPage("/login")
//                    .usernameParameter("login") //the username parameter in the queryString, default is 'username'
//                    .passwordParameter("password") //the password parameter in the queryString, default is 'password'
//                    .successHandler(this::loginSuccessHandler)
//                    .failureHandler(this::loginFailureHandler)
//                    .defaultSuccessUrl("/users")

                .and()
                    .logout();
//                    .logoutUrl("/logout")
//                    .logoutSuccessHandler(this::logoutSuccessHandler)
//                    .invalidateHttpSession(true);

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

//    private void loginSuccessHandler(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            Authentication authentication) throws IOException {
//
//        response.setStatus(HttpStatus.OK.value());
//        objectMapper.writeValue(response.getWriter(), "Yayy you logged in!");
//    }
//
//    private void loginFailureHandler(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            AuthenticationException e) throws IOException {
//
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        objectMapper.writeValue(response.getWriter(), "Nopity nop!");
//    }
//
//    private void logoutSuccessHandler(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            Authentication authentication) throws IOException {
//
//        response.setStatus(HttpStatus.OK.value());
//        objectMapper.writeValue(response.getWriter(), "Bye!");
//    }
}
