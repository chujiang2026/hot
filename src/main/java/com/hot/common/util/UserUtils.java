package com.hot.common.util;

import com.hot.common.constant.AuthConstant;
import com.hot.modules.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 当前登录用户工具：根据请求头中的 token 从 Redis 取出登录用户。
 */
@Component
public class UserUtils {

    private static RedisUtils redisUtils;

    @Autowired
    public void setRedisUtils(RedisUtils redisUtils) {
        UserUtils.redisUtils = redisUtils;
    }

    /** 获取当前登录用户，未登录返回 null。 */
    public static SysUser getCurrentUser(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        // 拦截器已把用户放入 request（同样来自 Redis），有则直接用，避免重复读
        Object attr = request.getAttribute(AuthConstant.CURRENT_USER);
        if (attr instanceof SysUser) {
            return (SysUser) attr;
        }
        // 否则按 token 从 Redis 取
        return getByToken(request.getHeader(AuthConstant.TOKEN_HEADER));
    }

    /** 获取当前登录用户，自动从上下文中取 request，未登录返回 null。 */
    public static SysUser getCurrentUser() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes == null ? null : getCurrentUser(attributes.getRequest());
    }

    /** 根据 token 从 Redis 取登录用户。 */
    private static SysUser getByToken(String token) {
        if (!StringUtils.hasText(token) || redisUtils == null) {
            return null;
        }
        Object value = redisUtils.get(AuthConstant.TOKEN_KEY_PREFIX + token);
        return value instanceof SysUser ? (SysUser) value : null;
    }
}
