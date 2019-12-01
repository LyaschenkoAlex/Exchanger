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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
                .antMatchers("/user_page/**")
                .access("hasRole('ROLE_USER')")
                .antMatchers("/login/**", "/register/**").access("anonymous")
                .antMatchers("/main_page")
                .access("permitAll")
                //end::authorizeRequests[]

                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/main_page")
                //end::customLoginPage[]
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")

                // tag::enableLogout[]
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/main_page")
                // end::enableLogout[]

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

        ;
//        http.formLogin().defaultSuccessUrl("/main_page", true);

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
