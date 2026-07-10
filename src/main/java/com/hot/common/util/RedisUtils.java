package com.hot.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 * 封装 string / hash / list / set 常用操作及过期、自增等。
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // ============================ key ============================

    /** 是否存在某个 key。 */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /** 删除一个或多个 key。 */
    public void delete(String... keys) {
        if (keys == null || keys.length == 0) {
            return;
        }
        if (keys.length == 1) {
            redisTemplate.delete(keys[0]);
        } else {
            redisTemplate.delete(Arrays.asList(keys));
        }
    }

    /** 按前缀批量删除 key（用于清除某一类缓存，如 menu_cache*）。 */
    public void deleteByPrefix(String prefix) {
        Set<String> keys = redisTemplate.keys(prefix + "*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    /** 设置过期时间（秒），小于等于 0 表示不过期。 */
    public boolean expire(String key, long seconds) {
        if (seconds <= 0) {
            return false;
        }
        return Boolean.TRUE.equals(redisTemplate.expire(key, seconds, TimeUnit.SECONDS));
    }

    /** 获取剩余过期时间（秒），返回 -1 表示永久，-2 表示不存在。 */
    public long getExpire(String key) {
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return expire == null ? -2 : expire;
    }

    // ============================ string ============================

    /** 存入键值，永不过期。 */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /** 存入键值并设置过期时间（秒），seconds <= 0 时按永不过期处理。 */
    public void set(String key, Object value, long seconds) {
        if (seconds > 0) {
            redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
    }

    /** 获取值。 */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /** 自增，delta 必须大于 0。 */
    public long increment(String key, long delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("递增因子必须大于 0");
        }
        Long val = redisTemplate.opsForValue().increment(key, delta);
        return val == null ? 0 : val;
    }

    /** 自减，delta 必须大于 0。 */
    public long decrement(String key, long delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("递减因子必须大于 0");
        }
        Long val = redisTemplate.opsForValue().increment(key, -delta);
        return val == null ? 0 : val;
    }

    // ============================ hash ============================

    /** 写入 hash 字段。 */
    public void hSet(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /** 读取 hash 字段。 */
    public Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /** 读取整个 hash。 */
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /** 删除 hash 中的一个或多个字段。 */
    public void hDelete(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    // ============================ list ============================

    /** 从右侧入队。 */
    public void lPush(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /** 获取 list 指定范围元素，0 到 -1 表示全部。 */
    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    // ============================ set ============================

    /** 向 set 添加元素。 */
    public void sAdd(String key, Object... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    /** 获取 set 全部元素。 */
    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }
}
