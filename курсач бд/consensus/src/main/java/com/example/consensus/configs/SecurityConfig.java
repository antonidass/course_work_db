package com.example.consensus.configs;


import com.example.consensus.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    private static final String COMPANY_ENDPOINT = "/api/v1/company/**";
    private static final String FORECAST_ENDPOINT = "/api/v1/forecast/**";
//    private static final String INDICATORS_ENDPOINT = "/api/v1/news/";
    private static final String NEWS_ENDPOINT = "/api/v1/news/**";
    private static final String USER_ENDPOINT = "/api/v1/user/**";
    private static final String ROLE_ENDPOINT = "/api/v1/role/**";


    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers(HttpMethod.GET, "/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers( "/login", "/register").permitAll()
                .antMatchers(USER_ENDPOINT, ROLE_ENDPOINT).hasAnyAuthority("admin")
                .antMatchers(HttpMethod.POST, COMPANY_ENDPOINT, FORECAST_ENDPOINT, NEWS_ENDPOINT).hasAnyAuthority("analyst")
                .antMatchers(HttpMethod.PUT, COMPANY_ENDPOINT, NEWS_ENDPOINT, FORECAST_ENDPOINT).hasAnyAuthority("analyst")
                .antMatchers(HttpMethod.DELETE, COMPANY_ENDPOINT, NEWS_ENDPOINT, FORECAST_ENDPOINT).hasAnyAuthority("analyst")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .logout().logoutSuccessUrl("/")
                .logoutSuccessHandler(logoutSuccessHandler())
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        ;
    }



    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }

}
