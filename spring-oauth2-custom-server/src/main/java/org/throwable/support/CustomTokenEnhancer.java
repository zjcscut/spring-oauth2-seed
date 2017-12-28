package org.throwable.support;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author throwable
 * @version v1.0
 * @description 自定义的token生成器,实质上是token的增强修改器,原来获取到的accessToken的value都是UUID
 * @since 2017/12/25 15:16
 */
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (accessToken instanceof DefaultOAuth2AccessToken) {
            DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
            token.setValue(generateNewToken());
            OAuth2RefreshToken refreshToken = token.getRefreshToken();
            if (null != refreshToken && refreshToken instanceof DefaultOAuth2RefreshToken) {
                token.setRefreshToken(new DefaultOAuth2RefreshToken(generateNewToken()));
            }
            Map<String, Object> additionalInformation = new HashMap<>(1);
            token.setAdditionalInformation(additionalInformation);
            additionalInformation.put("client_id", authentication.getOAuth2Request().getClientId());
            return token;
        }
        return accessToken;
    }

    /**
     * 去掉所有'-'
     *
     * @return String
     */
    public String generateNewToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
