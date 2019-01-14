package com.kunpenggu.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("root").password("root").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("sa").password("sa").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/", "/console/**").permitAll()
               // .and()
               // .authorizeRequests().antMatchers("/console/**").hasRole("ADMIN")//allow h2 console access to admins only
               // .anyRequest().authenticated()//all other urls can be access by any authenticated role
             //   .and()
             //   .csrf().ignoringAntMatchers("/console/**")
                .and()
                .cors().disable();

        //http.csrf().disable().authorizeRequests().antMatchers("/", "/console/**").permitAll();
        http.headers().frameOptions().disable();
    }


}
