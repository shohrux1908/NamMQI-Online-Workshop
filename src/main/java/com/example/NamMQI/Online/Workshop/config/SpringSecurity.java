package com.example.NamMQI.Online.Workshop.config;

import com.example.NamMQI.Online.Workshop.security.CustomUserDetailsService;
import com.example.NamMQI.Online.Workshop.service.AdminService;
import com.example.NamMQI.Online.Workshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailsService userService;
    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth=new DaoAuthenticationProvider();
        auth.setUserDetailsService( userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(
                        "/register*",
                        "/",
                        "/css/",
                        "/static/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }


}










/////////












//
//@Configuration
//@EnableWebSecurity
//public class SpringSecurity {
//
//    public static final String[] ENDPOINTS_WHITELIST = {
//            "/static/**",
//            "/register/**",
//            "/webjars/**",
//            "/adminlogin/**",
//            "/"
//
//    };
//
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Bean
//    public static PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeHttpRequests((authorize) ->
//
//                        authorize.requestMatchers(ENDPOINTS_WHITELIST).permitAll()
//                                .requestMatchers("/cabinet","/apply","/messageSave").hasRole("ADMIN")
//                                .requestMatchers("/admin","/users/delete/","user_message").permitAll()
//
//                ).formLogin(
//                        form -> form
//                                .loginPage("/login")
//                                .loginProcessingUrl("/login")
//                                .defaultSuccessUrl("/cabinet",true)
//
//                                .permitAll()
//
//
//                ).logout(
//                        logout -> logout
//                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                                .permitAll()
//                );
//
//        return http.build();
//    }
//
//
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder());
//    }
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService);
//        return authenticationProvider;
//    }
//}
//







//    public static final String LOGIN_URL = "/login";
//    public static final String LOGOUT_URL = "/logout";
//    public static final String DEFAULT_SUCCESS_URL = "/";

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests(request ->
//                        request.requestMatchers(ENDPOINTS_WHITELIST).permitAll()
//                                .anyRequest().authenticated())
//                .csrf().disable()
//                .formLogin(form -> form
//                        .loginPage(LOGIN_URL)
//                        .loginProcessingUrl(LOGIN_URL)
//                        .defaultSuccessUrl(DEFAULT_SUCCESS_URL)
//
////                             .requestMatchers("/admin").hasRole("ADMIN")
//                ).formLogin(
//                        form -> form
//                                .loginPage("/login")
//                                .loginProcessingUrl("/login")
//                                .defaultSuccessUrl("/mycabinet")
//                                .permitAll()
//                ).logout(
//                        logout -> logout
//                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                                .permitAll()
//                );
//        return http.build();
//    }
