package org.throwable.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/12/24 14:21
 */
@EnableResourceServer
@Configuration
public class OAuthResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(OAuthAuthorizationProperties.RESOURCE_ID);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/api/**")
				//read scope
				.access("#oauth2.hasScope('read')")
				//write scope
				.antMatchers(HttpMethod.POST, "/api/**")
				.access("#oauth2.hasScope('write')")
				.antMatchers("/api/**")
				//authority
				.hasAuthority(OAuthAuthorizationProperties.ROLE);
	}
}
