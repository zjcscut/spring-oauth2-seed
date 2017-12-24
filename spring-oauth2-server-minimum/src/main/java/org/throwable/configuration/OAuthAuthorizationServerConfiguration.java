package org.throwable.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/12/23 11:37
 */
@Configuration
@EnableAuthorizationServer
public class OAuthAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				//客户端ID
				.withClient(OAuthAuthorizationProperties.OAUTH_CLIENT_ID)
				//秘钥
				.secret(OAuthAuthorizationProperties.OAUTH_CLIENT_SECRET)
				//资源ID
				.resourceIds(OAuthAuthorizationProperties.RESOURCE_ID)
				//客户端的访问范围
				.scopes(OAuthAuthorizationProperties.SCOPES)
				//客户端可以使用的权限
				.authorities(OAuthAuthorizationProperties.ROLE)
				//授权的类型
				.authorizedGrantTypes("authorization_code", "refresh_token")
				//重定向的uri
				.redirectUris("http://default-oauth-callback.com")
				//accessToken有效时长
				.accessTokenValiditySeconds(60 * 30)
				//refreshToken有效时长
				.refreshTokenValiditySeconds(60 * 60 * 24);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//允许通过Form表单传递客户端验证授权参数，否则要把basic的验证参数进行base64放在header
		security.allowFormAuthenticationForClients();
	}
}
