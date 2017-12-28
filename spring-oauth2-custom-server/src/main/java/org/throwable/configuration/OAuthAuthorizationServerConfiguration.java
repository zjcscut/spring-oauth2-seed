package org.throwable.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.throwable.support.CustomMemoryAuthorizationCodeServices;
import org.throwable.support.CustomTokenEnhancer;

import javax.annotation.PostConstruct;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/12/23 11:37
 */
@Configuration
@EnableAuthorizationServer
public class OAuthAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthorizationEndpoint authorizationEndpoint;

    private static final String CUSTOM_APPROVAL_PAGE = "forward:/oauth/custom-approval-page";
    private static final String CUSTOM_ERROR_PAGE = "forward:/oauth/custom-error-page";

    private static final Long CODE_EXPIRATION_SECONDS = 5L;

    /**
     * 这里的目的是修改授权页和授权错误跳转的页面
     */
//    @PostConstruct
//    public void init() {
//        this.authorizationEndpoint.setUserApprovalPage(CUSTOM_APPROVAL_PAGE);
//        this.authorizationEndpoint.setErrorPage(CUSTOM_ERROR_PAGE);
//    }

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

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //自定义随机数生成
        endpoints.authorizationCodeServices(authorizationCodeServices())
                //自定义token生成器,实际上用于加工token
                .tokenEnhancer(tokenEnhancer());
    }

    /**
     * 自定义随机数生成服务
     *
     * @return AuthorizationCodeServices
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new CustomMemoryAuthorizationCodeServices(CODE_EXPIRATION_SECONDS);
    }

    @Bean
    public CustomTokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }
}
