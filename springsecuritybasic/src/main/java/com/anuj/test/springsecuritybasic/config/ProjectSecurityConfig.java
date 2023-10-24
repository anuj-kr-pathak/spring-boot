package com.anuj.test.springsecuritybasic.config;

import com.anuj.test.springsecuritybasic.filter.CsrfCookieFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.Collections;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{

        //Custom Configuration for flow ..
        http
                //create a jessionid and share with UI so every time Ui sends jessionid as well otherwise we have to enter cred for all secured apis
                .securityContext().requireExplicitSave(false)
                .and().sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                       config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                })
                //ignoring csrf attch on /contact because it is public api
                //CookieCsrfTokenRepository.withHttpOnlyFalse() to allow js or ui code can read cookie value
                .and().csrf().ignoringAntMatchers("/contact","register")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //telling we have spring security we have filter for cookie storage and save
                //execute csrfCookieFilter after basic authenticationFilter
                .and().addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)

                    //authorization by default on in build authorization
//                .authorizeHttpRequests( (auth) -> auth
//                        .antMatchers("/myAccount","/myBalance","/myLoans","/myCards","/user").authenticated()
//                        .antMatchers("/notices","/contacts","/register").permitAll()
//                ).httpBasic(Customizer.withDefaults());

        //custom authorization of authority
//                .authorizeHttpRequests((auth) -> {
//                    try {
//                        auth.antMatchers("/myLoans").hasAuthority("VIEWACCOUNT")
//                                .antMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT","VIEWBALANCE")
//                                        .antMatchers("/myLoans").hasAuthority("VIEWLOANS")
//                                        .antMatchers("/myCards").hasAuthority("VIEWCARDS")
//                                        .antMatchers("/user").authenticated()
//                                        .antMatchers("/notices","/contacts","/register").permitAll()
//                                        .and().formLogin().and().httpBasic();
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                });

        // custom role
                .authorizeHttpRequests((auth) -> {
                    try {
                        auth.antMatchers("/myLoans").hasRole("USER")
                                .antMatchers("/myBalance").hasAnyRole("USER","ADMIN")
                                .antMatchers("/myLoans").hasRole("USER")
                                .antMatchers("/myCards").hasRole("MANAGER")
                                .antMatchers("/user").authenticated()
                                .antMatchers("/notices","/contacts","/register").permitAll()
                                .and().formLogin().and().httpBasic();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });


//        //denying all request
//        http.authorizeHttpRequests((auth) -> auth.anyRequest().denyAll())
//                .httpBasic(Customizer.withDefaults());
//
//        //Allowing all request
//        http.authorizeHttpRequests((auth) -> auth.anyRequest().permitAll())
//                .httpBasic(Customizer.withDefaults());
//
        return (SecurityFilterChain) http.build();
    }

/*    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }*/

/*    @Bean
    public InMemoryUserDetailsManager userDetailsService(){

        InMemoryUserDetailsManager userDetailService = new InMemoryUserDetailsManager();
        *//*
            Approach 1 where we use withDefaultPasswordEncoder() is deprecated
         *//*
        *//*
            Approach 2 where we don't define while creating but using bean passwordEncoder
         *//*

        UserDetails admin = User.withUsername("admin").password("12345").authorities("admin").build();
        UserDetails user = User.withUsername("user").password("12345").authorities("read").build();
        userDetailService.createUser(admin);
        userDetailService.createUser(user);
        return userDetailService;
    }*/

    /*
    always use for non prod env
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
