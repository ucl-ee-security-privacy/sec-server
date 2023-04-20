package ucl.ee.sec.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
//                .requestMatchers("/admin_modify.html","/admin_modify")
//                .hasRole("ADMIN")
                .requestMatchers("/**","/comment/**")
                .permitAll()
//                .anyRequest()
//                .authenticated()
//                // 代表拦截所有请求，另外一种方式：（antMatchers("/**")）
//
//                .and()
//                .formLogin()
//                .loginPage("/admin_login.html")
//                .loginProcessingUrl("/admin_login")
//                .and()
//                .logout()
//                .logoutUrl("/logout") //注销成功，重定向到该路径下
//                .logoutSuccessUrl("/index.html")
//
////使得session失效
//
//                .invalidateHttpSession(true)
//
////清除认证信息
//
//                .clearAuthentication(true)
//
//                .and()
//
////进行会话管理
//
//                .sessionManagement()
//
//                .sessionFixation()
//
//                .migrateSession()
                .and()
                .headers()
                    .xssProtection()

                .and()
                .contentSecurityPolicy("script-src 'self'")
                .and()
                .frameOptions().sameOrigin()
                .and()
//                .addFilterAfter( new XssFilter(), CsrfFilter.class)
                .httpBasic()
                .and()
                .csrf().ignoringRequestMatchers("/login","/comment/submit","/admin_modify","/admin_login")

//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

        ;

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/index.html", "/login.html","/comment.html","/","/single.html");
    }

    /**
     * 在Spring security 5 之后需要设置密码解析器，
     * 如果不设置会报错，一般情况下会用Md5.本文采用的无密码验证
     * @return
     */
//    @Bean
//    public static NoOpPasswordEncoder passwordEncoder() {
//        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }



//    @Bean
//    public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        // remember the password that is printed out and use in the next step
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String psw= "password";
//        psw=encoder.encode(psw);
//        System.out.println("password:"+psw);
//
//        UserDetails user = User.withUsername("admin_ee")
//                .password(psw)
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user);
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



