package ucl.ee.sec.config;



import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import ucl.ee.sec.config.XssFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers("/admin_modify.html")

                .hasRole("ADMIN")
                .requestMatchers("/admin_modify")
                .hasRole("ADMIN")
                .requestMatchers("/**")

                .permitAll()

                .anyRequest()

                .authenticated()
                // 代表拦截所有请求，另外一种方式：（antMatchers("/**")）

                .and()
                .formLogin()
                .loginPage("/login.html")
                .and()
                .logout()
                .logoutUrl("/logout") //注销成功，重定向到该路径下
                .logoutSuccessUrl("/index.html")

//使得session失效

                .invalidateHttpSession(true)

//清除认证信息

                .clearAuthentication(true)

                .and()

//进行会话管理

                .sessionManagement()

                .sessionFixation()

                .migrateSession()
                .and()
                .headers().xssProtection().and()
                .and()
                .addFilterAfter((Filter) new XssFilter(), CsrfFilter.class)
                .httpBasic()
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

        ;

        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        // configure Web security...
//    }

    /**
     * 在Spring security 5 之后需要设置密码解析器，
     * 如果不设置会报错，一般情况下会用Md5.本文采用的无密码验证
     * @return
     */
//    @Bean
//    public static NoOpPasswordEncoder passwordEncoder() {
//        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }



    @Bean
    public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService());
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
//    }
//
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //auth.userDetailsService(userDetailsService());
//        auth.authenticationProvider(authenticationProvider());
//    }


//    @Bean
//    public DataSource datasource() {
//        return new EmbeddedDatabaseBuilder()
////                .setType(EmbeddedDatabaseType.H2)
//                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
//                .build();
//    }
//
//    @Bean
//    public UserDetailsManager users(DataSource dataSource) {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//        users.createUser(user);
//        return users;
//    }


}



