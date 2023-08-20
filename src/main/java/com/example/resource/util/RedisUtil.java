package com.example.resource.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public  class RedisUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // ========== basic ==========

    /**
     * 设置键值对的过期时间
     *
     * @param key  键名
     * @param time 时间
     * @param unit 时间单位 秒或毫秒
     * @return true 成功 false 失败
     */
    public boolean setExpire(String key, long time, TimeUnit unit) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, unit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取键的过期时间
     *
     * @param key
     * @param unit 时间单位 秒或毫秒
     * @return
     */
    public long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 删除传入的 keys
     *
     * @param keys
     */
    public void del(String... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(keys));
            }
        }
    }
    // ========== string ==========

    /**
     * 获取 key 对应的 value
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置键值对
     *
     * @param key
     * @param value
     * @return 成功 true 失败 false
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置键值对并设置过期时间
     * @param key
     * @param value
     * @param time 秒或毫秒数
     * @param unit 时间单位
     * @return 成功 true 失败 false
     */
    public boolean setWithExpire(String key, Object value, long time, TimeUnit unit) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, unit);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将 key 对应的 value 自增 step
     * @param key
     * @param step
     * @return
     */
    public long incr(String key, long step) {
        if (step < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, step);
    }

    /**
     * 将 key 对应的 value 自减 step
     * @param key
     * @param step
     * @return
     */
    public long decr(String key, long step) {
        if (step < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -step);
    }

    // ========== Map ==========

    /**
     * 获取 hash 表 field 字段的值
     * @param key hash 表的键
     * @param field hash 表中的字段
     * @return hash 表中 field 对应的值
     */
    public Object hashGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }
    /**
     * 获取 hash 表 field 字段
     * @param key hash 表的键
     * @return hash 表中 field值
     */

    /**
     * 获取 hash 表所有的值
     * @param key hash 表的键
     * @return hash 表中所有的值
     */
    public Map<Object, Object> hashGetAllMember(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 设置多个 hash 表的值
     * @param key hash 表的 key
     * @param members 要添加的键值对
     * @return 成功 true 失败 fasle
     */
    public boolean hashSetMembers(String key, Map<String, Object> members) {
        try {
            redisTemplate.opsForHash().putAll(key, members);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置多个 hash 表的值并设置 hash 表的过期时间
     * @param key hash 表的键
     * @param members 要添加的键值对
     * @param time 秒数或毫秒数
     * @param unit 时间单位秒或毫秒
     * @return 成功 true 失败 false
     */
    public boolean hashSetMembersWithExpire(String key, Map<String, Object> members, long time, TimeUnit unit) {
        try {
            redisTemplate.opsForHash().putAll(key, members);
            if (time > 0) {
                setExpire(key, time, unit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将 hash 表 field 的值设置为 value
     * @param key hash 表的键
     * @param field hash 表中的字段
     * @param value 要设置的值
     * @return 成功 true 失败 false
     */
    public boolean hashSet(String key, String field, Object value) {
        try {
            redisTemplate.opsForHash().put(key, field, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置 hash 表指定 field 的值并设置 hash 表的过期时间
     * @param key hash 表的键
     * @param field hash 表中的字段
     * @param value 要添加的值
     * @param time 秒数或毫秒数
     * @param unit 时间单位秒或毫秒
     * @return 成功 true 失败 false
     */
    public boolean hashSetWithExpire(String key, String field, Object value, long time, TimeUnit unit) {
        try {
            redisTemplate.opsForHash().put(key, field, value);
            if (time > 0) {
                setExpire(key, time, unit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除 hash 表的字段 fields
     * @param key hash 表的键
     * @param fields hash 表中的字段
     */
    public void hashDel(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 查询 hash 表中是否存在 field 字段
     * @param key hash 表的键
     * @param field hash 表中的字段
     * @return 存在 true 不存在 false
     */
    public boolean hashHasField(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 将 hash 表中 field 字段的值自增 step
     * @param key hash 表的键
     * @param field hash 表中的字段
     * @param step 步长
     * @return 自增后 value 的值
     */
    public double hashIncr(String key, String field, double step) {
        return redisTemplate.opsForHash().increment(key, field, step);
    }

    /**
     * 将 hash 表中 field 字段的值自减 step
     * @param key hash 表的键
     * @param field hash 表中的字段
     * @param step 步长
     * @return 自减后 value 的值
     */
    public double hashDecr(String key, String field, double step) {
        return redisTemplate.opsForHash().increment(key, field, -step);
    }

    // ========== set ==========

    /**
     * 获取 set 中的值
     * @param key set 的键
     * @return set 中的值
     */
    public Set<Object> getSetValue(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询 set 中是否有 value
     * @param key set 的键
     * @param value 要查询的值
     * @return 存在返回 true 不存在或报错返回 false
     */
    public boolean setHasValue(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将 value 添加到 set 中
     * @param key set 的键
     * @param values 要存入 set 中的值
     * @return 成功存入 set 中的值的个数或 0
     */
    public long addSetValue(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将 value 添加到 set 中并设置 set 的过期时间
     * @param key set 的键
     * @param time 秒数或毫秒数
     * @param unit 时间单位 秒或毫秒
     * @param values 要存入 set 中的值
     * @return 成功存入 set 中的值的个数或 0
     */
    public long addSetValueWithExpire(String key, long time, TimeUnit unit, Object... values) {
        try {
            long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                setExpire(key, time, unit);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取 set 的长度
     * @param key set 的键
     * @return set 的长度或 0
     */
    public long getSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 删除 set 中的 values （原子操作，只能全部成功或者失败）
     * @param key set 的键
     * @param values 要存入 set 中的值
     * @return 成功存入 set 中的值的数量或 0
     */
    public long removeSetValues(String key, Object... values) {
        try {
            long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    // ========== List ==========

    /**
     * 获取 list 中的值，传入 0，-1获取 list 中所有的值
     * @param key list 的键
     * @param start 起始下标
     * @param end 结束下标
     * @return list 中指定范围的值或 null
     */
    public List<Object> getListValues(String key,long start,long end){
        try{
            return redisTemplate.opsForList().range(key,start,end);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询 list 的长度
     * @param key list 的键
     * @return list 的长度或 0
     */
    public long getListLength(String key){
        try {
            return redisTemplate.opsForList().size(key);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过下标查询 list 中的值
     * @param key list 的键
     * @param index list 的下标
     * @return list 中的值或 null
     */
    public Object getListValueByIndex(String key,long index){
        try{
            return redisTemplate.opsForList().index(key,index);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将 value push 到一个 list 中
     * @param key list 的键
     * @param value 要存入的值
     * @return 成功 true 失败 false
     */
    public boolean pushValue(String key,Object value){
        try {
            redisTemplate.opsForList().rightPush(key,value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将 value push 到一个 list 中并设置 list 的过期时间
     * @param key list 的键
     * @param value 要存入的值
     * @param time 秒数或毫秒数
     * @param unit 时间单位秒或毫秒
     * @return 成功 true 失败 false
     */
    public boolean pushValueWithExpire(String key,Object value,long time,TimeUnit unit){
        try{
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0){
                setExpire(key,time,unit);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将一个 list 中的所有值存入 redis list 中
     * @param key redis list 的键
     * @param list 要存入的值组成的 list
     * @return 成功 true 失败 false
     */
    public boolean pushListValue(String key, List<Object> list){
        try{
            redisTemplate.opsForList().rightPushAll(key,list);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将一个 list 中的所有值存入 redis list 中并设置 redis list 的过期时间
     * @param key redis list 的键
     * @param list 要存入的值组成的 list
     * @param time 秒数或毫秒数
     * @param unit 时间单位秒或毫秒
     * @return 成功 true 失败 false
     */
    public boolean pushListValueWithExpire(String key, List<Object> list,long time,TimeUnit unit){
        try{
            redisTemplate.opsForList().rightPushAll(key,list);
            if (time > 0){
                setExpire(key,time,unit);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean setListItemByIndex(String key, long index, Object value){
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除 count 个 list 中的 value
     * @param key list 的键
     * @param count 删除个数
     * @param value 要删除的值
     * @return 成功删除的个数
     */
    public long removeListItem(String key,long count,Object value){
        try{
            long removeCount = redisTemplate.opsForList().remove(key, count, value);
            return removeCount;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
