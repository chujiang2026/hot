package com.hot.common.constant;

import java.time.Duration;

/**
 * 登录鉴权相关常量。
 */
public final class AuthConstant {

    private AuthConstant() {
    }

    /** 客户端携带令牌的请求头名称。 */
    public static final String TOKEN_HEADER = "token";

    /** Redis 中令牌的 key 前缀，完整 key 为 login:token:{token}。 */
    public static final String TOKEN_KEY_PREFIX = "login:token:";

    /** 令牌有效期，每次访问会续期。 */
    public static final Duration TOKEN_EXPIRE = Duration.ofHours(2);

    /** 当前登录用户在 request 中的属性名。 */
    public static final String CURRENT_USER = "currentUser";

    public static final String USER_CACHE = "user_cache";
    public static final String MENU_CACHE = "menu_cache";
    public static final String MODU_CACHE = "modu_cache";
    public static final String BUTT_CACHE = "butt_cache";
}
