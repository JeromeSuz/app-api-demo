package com.example.util.cache;

import redis.clients.jedis.JedisPubSub;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CacheI {

    boolean put(String key, Object value);

    boolean put(String key, Object value, int secondsToLive);

    Object get(String key);

    boolean delete(String key);

    /**
     * 返回列表的长度
     *
     * @param key
     * @return 返回list的长度
     */
    long llen(String key);

    /**
     * 列表表头插入一个或多个值
     *
     * @param key
     * @param str 可以使一个string 也可以使string数组
     * @return 返回list的value个数
     */
    long lpush(String key, String str);

    /**
     * 列表表尾插入一个或多个值
     *
     * @param key
     * @param str
     * @return
     */
    long rpush(String key, String str);

    /**
     * 列表表尾移除并返回一个元素
     *
     * @param key
     * @return
     */
    String rpop(String key);

    /**
     * 阻塞式，移除并返回列表 key 的头元素。
     *
     * @param key
     * @return
     */
    String blpop(int timeout, String key);

    /**
     * 消息推送
     *
     * @param channel
     * @param message
     * @return
     */
    long publish(String channel, String message);

    /**
     * 消息订阅
     *
     * @param jedisPubSub
     * @param patterns
     */
    void psubscribe(JedisPubSub jedisPubSub, String... patterns);

    /**
     * 通过key 对value进行加值+1操作,当value不是int类型时会返回错误,当key不存在是则value为1
     *
     * @param key
     * @return 加值后的结果
     */
    Long incr(String key);

    /**
     * 对key的值做减减操作,如果key不存在,则设置key为-1
     *
     * @param key
     * @return
     */
    Long decr(String key);

    /**
     * 通过key保留list中从strat下标开始到end下标结束的value值
     *
     * @param key
     * @param start
     * @param end
     * @return 成功返回OK
     */
    String ltrim(String key, long start, long end);

    /**
     * 通过key向zset中添加value,score,其中score就是用来排序的
     * 如果该value已经存在则根据score更新元素
     *
     * @param key
     * @param score
     * @param member
     * @return
     */
    Long zadd(String key, double score, String member);

    /**
     * 通过key向zset中添加value,score,其中score就是用来排序的
     * 如果该value已经存在则根据score更新元素
     *
     * @param key
     * @param scoreMembers
     * @return
     */
    Long zadd(String key, Map<String, Double> scoreMembers);

    /**
     * 通过key删除在zset中指定的value
     *
     * @param key
     * @param members 可以使一个string 也可以是一个string数组
     * @return
     */
    Long zrem(String key, String... members);

    /**
     * 通过key将获取score从start到end中zset的value
     * socre从大到小排序
     * 当start为0 end为-1时返回全部
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set<String> zrevrange(String key, long start, long end);

    /**
     * 通过key返回指定score内zset中的value
     *
     * @param key
     * @param max
     * @param min
     * @param offset 偏移量
     * @param count  个数
     * @return
     */
    Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count);

    /**
     * 通过key将获取score从start到end中zset的value
     * socre从小到大排序
     * 当start为0 end为-1时返回全部
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set<String> zrange(String key, long start, long end);

    /**
     * 返回指定区间内zset中value的数量
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    Long zcount(String key, double min, double max);

    /**
     * 通过key向指定的set中添加value
     *
     * @param key
     * @param members 可以是一个String 也可以是一个String数组
     * @return 添加成功的个数
     */
    Long sadd(String key, String... members);

    /**
     * 通过key判断value是否是set中的元素
     *
     * @param key
     * @param member
     * @return
     */
    Boolean sismember(String key, String member);

    /**
     * 通过key删除set中对应的value值
     *
     * @param key
     * @param members 可以是一个String 也可以是一个String数组
     * @return 删除的个数
     */
    Long srem(String key, String... members);

    /**
     * 给key 设置过期时间
     *
     * @param key
     * @param seconds
     * @return
     */
    Long expire(final String key, final int seconds);

    /**
     * 通过key给指定的value加值,如果key不存在,则这是value为该值</p>
     *
     * @param key
     * @param integer
     * @return
     */
    Long incrBy(String key, Long integer);

    /**
     * 通过key获取set中 count 个随机的value,不删除元素
     *
     * @param key
     * @param count
     * @return
     */
    List<String> srandmember(String key, int count);

    /**
     * 通过key给field设置指定的值,如果key不存在,则先创建
     *
     * @param key
     * @param field 字段
     * @param value
     * @return 如果存在返回0 异常返回null
     */
    Long hset(String key, String field, String value);

    /**
     * 通过key同时设置 hash的多个field
     *
     * @param key
     * @param hash
     * @return 返回OK 异常返回null
     */
    String hmset(String key, Map<String, String> hash);

    /**
     * 通过key 和 field 获取指定的 value
     *
     * @param key
     * @param field
     * @return 没有返回null
     */
    String hget(String key, String field);

    /**
     * 通过key获取所有的field和value
     *
     * @param key
     * @return
     */
    Map<String, String> hgetall(String key);

    /**
     * 通过key 删除指定的 field
     *
     * @param key
     * @param fields 可以是 一个 field 也可以是 一个数组
     * @return
     */
    Long hdel(String key, String... fields);

    /**
     * 以事务的方式，对某个key执行incr，并且设置ttl，返回incr和expire的执行结果
     * 如果有其他客户端对key进行修改，本次事务会被取消，返回null
     *
     * @param key
     * @param ttl 单位秒
     * @return
     */
    List<Object> incrAndExpire(String key, int ttl);

}
