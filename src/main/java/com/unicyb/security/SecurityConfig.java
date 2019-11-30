//tag::securityConfigOuterClass[]
package com.unicyb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

//end::securityConfigOuterClass[]
//tag::baseBonesImports[]
//end::baseBonesImports[]

//tag::securityConfigOuterClass[]

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//end::securityConfigOuterClass[]

    //tag::customUserDetailsService[]
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/user_info/**")
                .access("hasRole('ROLE_USER')")
                .antMatchers("/login/**", "/register/**").access("anonymous")
                .antMatchers("/courses ")
                .access("permitAll")
                //end::authorizeRequests[]

                .and()
                .formLogin()
                .loginPage("/login")
                //end::customLoginPage[]

                // tag::enableLogout[]
                .and()
                .logout()
                .logoutSuccessUrl("/")
                // end::enableLogout[]

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

        ;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new StandardPasswordEncoder("53cr3t");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());

    }

}
