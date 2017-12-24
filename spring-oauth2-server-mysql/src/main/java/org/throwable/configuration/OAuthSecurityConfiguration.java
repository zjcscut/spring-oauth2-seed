package org.throwable.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/12/24 14:25
 */
@Configuration
@EnableWebSecurity
public class OAuthSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//开启了basic验证
		auth.inMemoryAuthentication()
				.withUser("user").password("123").roles("USER")
				.and()
				.withUser("admin").password("123").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
				.antMatchers("/oauth/authorize").authenticated()
				.and()
				.httpBasic().realmName("OAuth Server");
	}
}
