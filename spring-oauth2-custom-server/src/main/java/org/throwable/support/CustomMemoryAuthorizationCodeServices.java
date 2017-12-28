package org.throwable.support;

import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
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
    private final Long expireSeconds;
    private final ConcurrentMap<String, OAuth2Authentication> authorizationCodeStore = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Long> expiration = new ConcurrentHashMap<>();

    public CustomMemoryAuthorizationCodeServices(Long expireSeconds) {
        this.expireSeconds = expireSeconds;
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
        expiration.put(code, getCurrentTimestamp() + expireSeconds * 1000);
        return code;
    }

    @Override
    public OAuth2Authentication consumeAuthorizationCode(String code) throws InvalidGrantException {
        Long expired = expiration.get(code);
        if (null != expired && isCodeExpired(expired)) {
            throw new InvalidGrantException("Invalid authorization code,authorization code is expired,value: " + code);
        }
        return super.consumeAuthorizationCode(code);
    }

    private boolean isCodeExpired(Long expired) {
        return getCurrentTimestamp() > expired;
    }

    private long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }
}
