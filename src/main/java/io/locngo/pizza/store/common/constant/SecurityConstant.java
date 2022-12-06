package io.locngo.pizza.store.common.constant;

import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class SecurityConstant {
    public static final long EXPIRATION_TIME_IN_MILLISECONDS = 432_000_000;

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String JWT_TOKEN_HEADER = "Jwt-Token";

    public static final String TOKEN_CANNOT_BE_VERIFIED_MESSAGE = "Token cannot be verified";

    public static final String ISSUER = "Mesoneer VN";

    public static final String AUDIENCE = "PIZZA STORE";

    public static final String AUTHORITIES = "authorities";

    public static final String UNAUTHORIZED_MESSAGE = "Unauthorized, you need to login";

    public static final String FOBBIDEN_MESSAGE = "You do not have permission";

    private static final String API_VERSION_REGEX = "/(api)/(v[0-9])/";

    public static final RequestMatcher[] PUBLIC_URLS = {
            RegexRequestMatcher.regexMatcher(API_VERSION_REGEX + "auth/register.*")
    };
}
