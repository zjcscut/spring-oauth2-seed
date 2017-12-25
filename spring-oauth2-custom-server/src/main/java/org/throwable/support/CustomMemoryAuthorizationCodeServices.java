package org.throwable.support;

import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author throwable
 * @version v1.0
 * @description 修改生成的随机数为16位
 * @since 2017/12/25 14:53
 */
public class CustomMemoryAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

    private RandomValueStringGenerator generator;
    private final ConcurrentMap<String, OAuth2Authentication> authorizationCodeStore = new ConcurrentHashMap<>();

    public CustomMemoryAuthorizationCodeServices() {
        this.generator = new RandomValueStringGenerator(16);
    }

    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        this.authorizationCodeStore.put(code, authentication);
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        return this.authorizationCodeStore.remove(code);
    }

    @Override
    public String createAuthorizationCode(OAuth2Authentication authentication) {
        String code = this.generator.generate();
        store(code, authentication);
        return code;
    }
}
