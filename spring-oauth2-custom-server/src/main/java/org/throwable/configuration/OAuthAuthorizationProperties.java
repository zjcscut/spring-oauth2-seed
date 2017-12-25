package org.throwable.configuration;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/12/24 14:03
 */
public abstract class OAuthAuthorizationProperties {

	public static final String OAUTH_CLIENT_ID = "oauth_client";
	public static final String OAUTH_CLIENT_SECRET = "oauth_client_secret";
	public static final String RESOURCE_ID = "my_resource_id";
	public static final String[] SCOPES = { "read", "write" };

	public static final String ROLE = "ROLE_USER";
}
