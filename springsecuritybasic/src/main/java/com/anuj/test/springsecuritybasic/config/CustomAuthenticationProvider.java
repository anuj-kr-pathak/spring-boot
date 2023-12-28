package com.anuj.test.springsecuritybasic.config;

import com.anuj.test.springsecuritybasic.model.Authority;
import com.anuj.test.springsecuritybasic.model.Customer;
import com.anuj.test.springsecuritybasic.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
//deleting we are perform the authentication of my end user to perform authentication with help of database
//@Component

public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd  = authentication.getCredentials().toString();
        List<Customer> customerList = customerRepository.findByEmail(username);
        if(customerList.size()>0){
            if(passwordEncoder.matches(pwd,customerList.get(0).getPwd())){
//                List<GrantedAuthority> authorities = new ArrayList<>();
//                authorities.add(new SimpleGrantedAuthority(customerList.get(0).getRole()));
//                return new UsernamePasswordAuthenticationToken(username,pwd,authorities);
                return new UsernamePasswordAuthenticationToken(username,pwd,getGrantedAuthorities(customerList.get(0).getAuthorities()));

            } else
                throw new BadCredentialsException("Invalid password");
        } else
            throw new BadCredentialsException("No user registered with this details ");
    }


    private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(Authority authority : authorities){
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //copied from dao authentication provider
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
