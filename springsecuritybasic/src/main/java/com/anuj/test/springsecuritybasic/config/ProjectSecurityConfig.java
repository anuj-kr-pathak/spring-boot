package com.anuj.test.springsecuritybasic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{

/*        //Default configuration which will secure all the requests
        http.authorizeHttpRequests().anyRequest()
                .authenticated().and().
                formLogin().and().httpBasic();*/


        //Custom Configuration for flow ..
        http.authorizeHttpRequests( (auth) -> auth
                        .antMatchers("/myAccount","myBalance","myLoans","myCards").authenticated()
                        .antMatchers("/notices","/contacts").permitAll()
                ).httpBasic(Customizer.withDefaults());

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

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(){

        InMemoryUserDetailsManager userDetailService = new InMemoryUserDetailsManager();
        /*
            Approach 1 where we use withDefaultPasswordEncoder() is deprecated
         */
        /*
            Approach 2 where we don't define while creating but using bean passwordEncoder
         */

        UserDetails admin = User.withUsername("admin").password("12345").authorities("admin").build();
        UserDetails user = User.withUsername("user").password("12345").authorities("read").build();
        userDetailService.createUser(admin);
        userDetailService.createUser(user);
        return userDetailService;
    }

    /*
    always use for non prod env
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
