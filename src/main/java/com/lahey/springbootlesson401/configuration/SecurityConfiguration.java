package com.lahey.springbootlesson401.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    BCryptPasswordEncoder myBCryptPasswordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        FormLoginConfigurer<HttpSecurity> httpSecurityFormLoginConfigurer =
        http.authorizeRequests().antMatchers("/").permitAll()
            .antMatchers("/users").hasAuthority("USER")
            .antMatchers("/admin").hasAuthority("ADMIN")
            .antMatchers("/eitheruseroradmin").hasAnyAuthority("USER","ADMIN")
            .anyRequest().authenticated()
            .and().formLogin();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{

        auth.inMemoryAuthentication().withUser("ausername").password(myBCryptPasswordEncoder()
                .encode("password")).authorities("USER","ADMIN")
                .and().passwordEncoder(myBCryptPasswordEncoder());
    }

}//end public class SecurityConfiguration extends WebSecurityConfigurerAdapter
