package com.hot.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码工具：基于 BCrypt 加密。
 * BCrypt 每次加密会生成随机盐并内嵌到结果中（形如 $2a$10$...，约 60 字符），
 * 校验时无需单独保存盐。
 */
public final class PasswordUtil {

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private PasswordUtil() {
    }

    /** 对明文密码加密，返回可直接存库的 BCrypt 摘要串。 */
    public static String encrypt(String rawPassword) {
        return ENCODER.encode(rawPassword);
    }

    /** 校验明文密码与已存储的 BCrypt 摘要是否匹配。 */
    public static boolean matches(String rawPassword, String stored) {
        if (rawPassword == null || stored == null || stored.isEmpty()) {
            return false;
        }
        return ENCODER.matches(rawPassword, stored);
    }

    public static void main(String[] args) {
    	System.out.println(encrypt("123456"));
    }
}
