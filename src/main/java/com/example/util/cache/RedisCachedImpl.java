package com.example.util.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * RedisCached 实现类
 *
 */
public final class RedisCachedImpl implements CacheI {

    protected RedisUtil redisCached = RedisUtil.getInstance();
    private static final Logger LOG = LoggerFactory.getLogger(RedisCachedImpl.class.getName());

    private static RedisCachedImpl redisCachedImpl = null;

    public RedisCachedImpl() {
    }

    public static RedisCachedImpl getInstance() {
        if (redisCachedImpl == null) {
            redisCachedImpl = new RedisCachedImpl();
        }
        return redisCachedImpl;
    }

    @Override
    public boolean put(String key, Object value) {
        LOG.info("put key=" + key + " start");
        String flag = redisCached.set(key, value.toString());
        LOG.info("put key=" + key + " end flag=" + flag);
        if (flag.equals("OK")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean put(String key, Object value, int secondsToLive) {
        LOG.info("put key=" + key + " start");
        String flag = "";
        if (secondsToLive == 0) {
            flag = redisCached.set(key, value.toString());
        } else {
            flag = redisCached.setex(key, value.toString(), secondsToLive);
        }
        LOG.info("put key=" + key + " end flag=" + flag);
        if (flag.equals("OK")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object get(String key) {
        LOG.info("get key=" + key + " start");
        Object obj = redisCached.get(key);
        LOG.info("get key=" + key + " end");
        return obj;
    }

    @Override
    public boolean delete(String key) {
        LOG.info("delete key=" + key + " start");
        long result = redisCached.del(key);
        LOG.info("delete key=" + key + " end result=" + result);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public long lpush(String key, String str) {
        LOG.info("lpush key=" + key + " start");
        long result = redisCached.lpush(key, str);
        LOG.info("lpush key=" + key + " end result=" + result);
        return result;
    }

    @Override
    public String rpop(String key) {
        LOG.info("rpop key=" + key + " start");
        String result = redisCached.rpop(key);
        LOG.info("rpop key=" + key + " end result=" + result);
        return result;
    }

    @Override
    public long publish(String channel, String message) {
        LOG.info("publish channel=" + channel + " start");
        long result = redisCached.publish(channel, message);
        LOG.info("publish channel=" + channel + " end result=" + result);
        return result;
    }

    @Override
    public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
        LOG.info("psubscribe patterns=" + patterns + " start");
        redisCached.psubscribe(jedisPubSub, patterns);
        LOG.info("psubscribe patterns=" + patterns + " end");
    }

    @Override
    public Long incr(String key) {
        LOG.info("incr key=" + key + " start");
        Long result = redisCached.incr(key);
        LOG.info("incr key=" + key + " end result=" + result);
        return result;
    }

    @Override
    public Long decr(String key) {
        LOG.info("decr key=" + key + " start");
        Long result = redisCached.decr(key);
        LOG.info("decr key=" + key + " end result=" + result);
        return result;
    }

    @Override
    public long rpush(String key, String str) {
        LOG.info("lpush key=" + key + " start");
        long result = redisCached.rpush(key, str);
        LOG.info("lpush key=" + key + " end result=" + result);
        return result;
    }

    @Override
    public String blpop(int timeout, String key) {
        LOG.info("blpop key=" + key + " start");
        String result = redisCached.blpop(timeout, key);
        LOG.info("blpop key=" + key + " end result=" + result);
        return result;
    }

    @Override
    public String ltrim(String key, long start, long end) {
        String result = redisCached.ltrim(key, start, end);
        LOG.info("ltrim key=" + key + " result=" + result);
        return result;
    }

    @Override
    public Long zadd(String key, double score, String member) {
        return redisCached.zadd(key, score, member);
    }

    @Override
    public Long zadd(String key, Map<String, Double> scoreMembers) {
        return redisCached.zadd(key, scoreMembers);
    }

    @Override
    public Long zrem(String key, String... members) {
        return redisCached.zrem(key, members);
    }

    @Override
    public Set<String> zrevrange(String key, long start, long end) {
        return redisCached.zrevrange(key, start, end);
    }

    @Override
    public Set<String> zrange(String key, long start, long end) {
        return redisCached.zrange(key, start, end);
    }

    /**
     * <p>通过key向指定的set中添加value</p>
     *
     * @param key
     * @param members 可以是一个String 也可以是一个String数组
     * @return 添加成功的个数
     */
    @Override
    public Long sadd(String key, String... members) {
        return redisCached.sadd(key, members);
    }

    /**
     * <p>通过key判断value是否是set中的元素</p>
     *
     * @param key
     * @param member
     * @return
     */
    @Override
    public Boolean sismember(String key, String member) {
        return redisCached.sismember(key, member);
    }

    /**
     * <p>通过key删除set中对应的value值</p>
     *
     * @param key
     * @param members 可以是一个String 也可以是一个String数组
     * @return 删除的个数
     */
    @Override
    public Long srem(String key, String... members) {
        return redisCached.srem(key, members);
    }

    /**
     * 给key设置过期时间
     */
    @Override
    public Long expire(String key, int seconds) {
        return redisCached.expire(key, seconds);
    }

    /**
     * <p>通过key给指定的value加值,如果key不存在,则这是value为该值</p>
     */
    @Override
    public Long incrBy(String key, Long integer) {
        return redisCached.incrBy(key, integer);
    }

    /**
     * <p>通过key获取set中 count 个随机的value,不删除元素</p>
     */
    @Override
    public List<String> srandmember(String key, int count) {
        return redisCached.srandmember(key, count);
    }

    @Override
    public Long hset(String key, String field, String value) {
        return redisCached.hset(key, field, value);
    }

    @Override
    public String hmset(String key, Map<String, String> hash) {
        return redisCached.hmset(key, hash);
    }

    @Override
    public String hget(String key, String field) {
        return redisCached.hget(key, field);
    }

    @Override
    public Map<String, String> hgetall(String key) {
        return redisCached.hgetall(key);
    }

    @Override
    public Long hdel(String key, String... fields) {
        return redisCached.hdel(key, fields);
    }

    /**
     * 获取jedis
     *
     * @return
     */
    public Jedis getJedis() {
        return redisCached.getJedis();
    }

    /**
     * 返回jedis
     *
     * @param jedis
     */
    public void returnJedis(Jedis jedis) {
        redisCached.returnJedis(jedis);
    }

    @Override
    public Long zcount(String key, double min, double max) {
        return redisCached.zcount(key, min, max);
    }

    @Override
    public Set<String> zrevrangeByScore(String key, double max, double min,
                                        int offset, int count) {
        return redisCached.zrevrangeByScore(key, max, min, offset, count);
    }

    @Override
    public long llen(String key) {
        return redisCached.llen(key);
    }

    @Override
    public List<Object> incrAndExpire(String key, int ttl) {
        RedisCachedImpl cache = new RedisCachedImpl();
        Jedis jedis = cache.getJedis();
        List<Object> exec = null;
        try {
            String watch = jedis.watch(key);
            LOG.info("key="+ key + " watch="+ watch);
            Transaction multi = jedis.multi();
            multi.incr(key);
            multi.expire(key, ttl);
            exec = multi.exec();
            LOG.info("key="+ key + " exec="+ exec);
            String unwatch = jedis.unwatch();
            LOG.info("key="+ key + " unwatch="+ unwatch);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            cache.returnJedis(jedis);
        }
        return exec;
    }
}
