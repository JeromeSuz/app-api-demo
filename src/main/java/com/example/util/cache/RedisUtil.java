package com.example.util.cache;

import com.example.util.MD5Utils;
import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.params.sortedset.ZAddParams;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * redis 工具类
 *
 */
public class RedisUtil {

    protected static RedisUtil redisCached = new RedisUtil();

    private RedisUtil() {
    }

    /**
     * 获取唯一实例.
     *
     * @return
     */
    public static RedisUtil getInstance() {
        return redisCached;
    }

    /**
     * <p>通过key获取储存在redis中的value</p>
     * <p>并释放连接</p>
     *
     * @param key
     * @return 成功返回value 失败返回null
     */
    public String get(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            value = jedis.get(key);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return value;
    }

    /**
     * <p>向redis存入key和value,并释放连接资源</p>
     * <p>如果key已经存在 则覆盖</p>
     *
     * @param key
     * @param value
     * @return 成功 返回OK 失败返回 0
     */
    public String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            return jedis.set(key, value);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
            return "0";
        } finally {
            JedisUtil.returnResource(jedis);
        }
    }


    /**
     * <p>删除指定的key,也可以传入一个包含key的数组</p>
     *
     * @param keys 一个key  也可以使 string 数组
     * @return 返回删除成功的个数
     */
    public Long del(String... keys) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            return jedis.del(keys);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
            return 0L;
        } finally {
            JedisUtil.returnResource(jedis);
        }
    }

    /**
     * <p>通过key向指定的value值追加值</p>
     *
     * @param key
     * @param str
     * @return 成功返回 添加后value的长度 失败 返回 添加的 value 的长度  异常返回0L
     */
    public Long append(String key, String str) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.append(key, str);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
            return 0L;
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>判断key是否存在</p>
     *
     * @param key
     * @return true OR false
     */
    public Boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            return jedis.exists(key);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
            return false;
        } finally {
            JedisUtil.returnResource(jedis);
        }
    }

    /**
     * <p>设置key value,如果key已经存在则返回0,nx==> not exist</p>
     *
     * @param key
     * @param value
     * @return 成功返回1 如果存在 和 发生异常 返回 0
     */
    public Long setnx(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            return jedis.setnx(key, value);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
            return 0L;
        } finally {
            JedisUtil.returnResource(jedis);
        }
    }

    /**
     * <p>设置key value并制定这个键值的有效期</p>
     *
     * @param key
     * @param value
     * @param seconds 单位:秒
     * @return 成功返回OK 失败和异常返回null
     */
    public String setex(String key, String value, int seconds) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.setex(key, seconds, value);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }


    /**
     * <p>通过key 和offset 从指定的位置开始将原先value替换</p>
     * <p>下标从0开始,offset表示从offset下标开始替换</p>
     * <p>如果替换的字符串长度过小则会这样</p>
     * <p>example:</p>
     * <p>value : bigsea@zto.cn</p>
     * <p>str : abc </p>
     * <P>从下标7开始替换  则结果为</p>
     * <p>RES : bigsea.abc.cn</p>
     *
     * @param key
     * @param str
     * @param offset 下标位置
     * @return 返回替换后  value 的长度
     */
    public Long setrange(String key, String str, int offset) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            return jedis.setrange(key, offset, str);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
            return 0L;
        } finally {
            JedisUtil.returnResource(jedis);
        }
    }


    /**
     * <p>通过批量的key获取批量的value</p>
     *
     * @param keys string数组 也可以是一个key
     * @return 成功返回value的集合, 失败返回null的集合 ,异常返回空
     */
    public List<String> mget(String... keys) {
        Jedis jedis = null;
        List<String> values = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            values = jedis.mget(keys);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return values;
    }

    /**
     * <p>批量的设置key:value,可以一个</p>
     * <p>example:</p>
     * <p>  obj.mset(new String[]{"key2","value1","key2","value2"})</p>
     *
     * @param keysvalues
     * @return 成功返回OK 失败 异常 返回 null
     */
    public String mset(String... keysvalues) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.mset(keysvalues);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>批量的设置key:value,可以一个,如果key已经存在则会失败,操作会回滚</p>
     * <p>example:</p>
     * <p>  obj.msetnx(new String[]{"key2","value1","key2","value2"})</p>
     *
     * @param keysvalues
     * @return 成功返回1 失败返回0
     */
    public Long msetnx(String... keysvalues) {
        Jedis jedis = null;
        Long res = 0L;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.msetnx(keysvalues);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>设置key的值,并返回一个旧值</p>
     *
     * @param key
     * @param value
     * @return 旧值 如果key不存在 则返回null
     */
    public String getset(String key, String value) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.getSet(key, value);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过下标 和key 获取指定下标位置的 value</p>
     *
     * @param key
     * @param startOffset 开始位置 从0 开始 负数表示从右边开始截取
     * @param endOffset
     * @return 如果没有返回null
     */
    public String getrange(String key, int startOffset, int endOffset) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.getrange(key, startOffset, endOffset);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key 对value进行加值+1操作,当value不是int类型时会返回错误,当key不存在是则value为1</p>
     *
     * @param key
     * @return 加值后的结果
     */
    public Long incr(String key) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.incr(key);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key给指定的value加值,如果key不存在,则这是value为该值</p>
     *
     * @param key
     * @param integer
     * @return
     */
    public Long incrBy(String key, Long integer) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.incrBy(key, integer);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>对key的值做减减操作,如果key不存在,则设置key为-1</p>
     *
     * @param key
     * @return
     */
    public Long decr(String key) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.decr(key);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>减去指定的值</p>
     *
     * @param key
     * @param integer
     * @return
     */
    public Long decrBy(String key, Long integer) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.decrBy(key, integer);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key获取value值的长度</p>
     *
     * @param key
     * @return 失败返回null
     */
    public Long serlen(String key) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.strlen(key);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key给field设置指定的值,如果key不存在,则先创建</p>
     *
     * @param key
     * @param field 字段
     * @param value
     * @return 如果存在返回0 异常返回null
     */
    public Long hset(String key, String field, String value) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.hset(key, field, value);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key给field设置指定的值,如果key不存在则先创建,如果field已经存在,返回0</p>
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hsetnx(String key, String field, String value) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.hsetnx(key, field, value);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key同时设置 hash的多个field</p>
     *
     * @param key
     * @param hash
     * @return 返回OK 异常返回null
     */
    public String hmset(String key, Map<String, String> hash) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.hmset(key, hash);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key 和 field 获取指定的 value</p>
     *
     * @param key
     * @param field
     * @return 没有返回null
     */
    public String hget(String key, String field) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.hget(key, field);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key 和 fields 获取指定的value 如果没有对应的value则返回null</p>
     *
     * @param key
     * @param fields 可以使 一个String 也可以是 String数组
     * @return
     */
    public List<String> hmget(String key, String... fields) {
        Jedis jedis = null;
        List<String> res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.hmget(key, fields);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key给指定的field的value加上给定的值</p>
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hincrby(String key, String field, Long value) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.hincrBy(key, field, value);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key和field判断是否有指定的value存在</p>
     *
     * @param key
     * @param field
     * @return
     */
    public Boolean hexists(String key, String field) {
        Jedis jedis = null;
        Boolean res = false;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.hexists(key, field);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key返回field的数量</p>
     *
     * @param key
     * @return
     */
    public Long hlen(String key) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.hlen(key);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;

    }

    /**
     * <p>通过key 删除指定的 field </p>
     *
     * @param key
     * @param fields 可以是 一个 field 也可以是 一个数组
     * @return
     */
    public Long hdel(String key, String... fields) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.hdel(key, fields);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key返回所有的field</p>
     *
     * @param key
     * @return
     */
    public Set<String> hkeys(String key) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.hkeys(key);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key返回所有和key有关的value</p>
     *
     * @param key
     * @return
     */
    public List<String> hvals(String key) {
        Jedis jedis = null;
        List<String> res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.hvals(key);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key获取所有的field和value</p>
     *
     * @param key
     * @return
     */
    public Map<String, String> hgetall(String key) {
        Jedis jedis = null;
        Map<String, String> res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.hgetAll(key);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key向list头部添加字符串</p>
     *
     * @param key
     * @param strs 可以使一个string 也可以使string数组
     * @return 返回list的value个数
     */
    public Long lpush(String key, String... strs) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.lpush(key, strs);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key向list尾部添加字符串</p>
     *
     * @param key
     * @param strs 可以使一个string 也可以使string数组
     * @return 返回list的value个数
     */
    public Long rpush(String key, String... strs) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.rpush(key, strs);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key在list指定的位置之前或者之后 添加字符串元素</p>
     *
     * @param key
     * @param where LIST_POSITION枚举类型
     * @param pivot list里面的value
     * @param value 添加的value
     * @return
     */
    public Long linsert(String key, LIST_POSITION where,
                        String pivot, String value) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.linsert(key, where, pivot, value);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key设置list指定下标位置的value</p>
     * <p>如果下标超过list里面value的个数则报错</p>
     *
     * @param key
     * @param index 从0开始
     * @param value
     * @return 成功返回OK
     */
    public String lset(String key, Long index, String value) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.lset(key, index, value);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key从对应的list中删除指定的count个 和 value相同的元素</p>
     *
     * @param key
     * @param count 当count为0时删除全部
     * @param value
     * @return 返回被删除的个数
     */
    public Long lrem(String key, long count, String value) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.lrem(key, count, value);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key保留list中从strat下标开始到end下标结束的value值</p>
     *
     * @param key
     * @param start
     * @param end
     * @return 成功返回OK
     */
    public String ltrim(String key, long start, long end) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.ltrim(key, start, end);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key从list的头部删除一个value,并返回该value</p>
     *
     * @param key
     * @return
     */
    public String lpop(String key) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.lpop(key);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key从list尾部删除一个value,并返回该元素</p>
     *
     * @param key
     * @return
     */
    public String rpop(String key) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.rpop(key);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key从一个list的尾部删除一个value并添加到另一个list的头部,并返回该value</p>
     * <p>如果第一个list为空或者不存在则返回null</p>
     *
     * @param srckey
     * @param dstkey
     * @return
     */
    public String rpoplpush(String srckey, String dstkey) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.rpoplpush(srckey, dstkey);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key获取list中指定下标位置的value</p>
     *
     * @param key
     * @param index
     * @return 如果没有返回null
     */
    public String lindex(String key, long index) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.lindex(key, index);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key返回list的长度</p>
     *
     * @param key
     * @return
     */
    public Long llen(String key) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.llen(key);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key获取list指定下标位置的value</p>
     * <p>如果start 为 0 end 为 -1 则返回全部的list中的value</p>
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lrange(String key, long start, long end) {
        Jedis jedis = null;
        List<String> res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.lrange(key, start, end);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key向指定的set中添加value</p>
     *
     * @param key
     * @param members 可以是一个String 也可以是一个String数组
     * @return 添加成功的个数
     */
    public Long sadd(String key, String... members) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.sadd(key, members);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key删除set中对应的value值</p>
     *
     * @param key
     * @param members 可以是一个String 也可以是一个String数组
     * @return 删除的个数
     */
    public Long srem(String key, String... members) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.srem(key, members);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key随机删除一个set中的value并返回该值</p>
     *
     * @param key
     * @return
     */
    public String spop(String key) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.spop(key);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key获取set中的差集</p>
     * <p>以第一个set为标准</p>
     *
     * @param keys 可以使一个string 则返回set中所有的value 也可以是string数组
     * @return
     */
    public Set<String> sdiff(String... keys) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.sdiff(keys);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key获取set中的差集并存入到另一个key中</p>
     * <p>以第一个set为标准</p>
     *
     * @param dstkey 差集存入的key
     * @param keys   可以使一个string 则返回set中所有的value 也可以是string数组
     * @return
     */
    public Long sdiffstore(String dstkey, String... keys) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.sdiffstore(dstkey, keys);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key获取指定set中的交集</p>
     *
     * @param keys 可以使一个string 也可以是一个string数组
     * @return
     */
    public Set<String> sinter(String... keys) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.sinter(keys);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key获取指定set中的交集 并将结果存入新的set中</p>
     *
     * @param dstkey
     * @param keys   可以使一个string 也可以是一个string数组
     * @return
     */
    public Long sinterstore(String dstkey, String... keys) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.sinterstore(dstkey, keys);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key返回所有set的并集</p>
     *
     * @param keys 可以使一个string 也可以是一个string数组
     * @return
     */
    public Set<String> sunion(String... keys) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.sunion(keys);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key返回所有set的并集,并存入到新的set中</p>
     *
     * @param dstkey
     * @param keys   可以使一个string 也可以是一个string数组
     * @return
     */
    public Long sunionstore(String dstkey, String... keys) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.sunionstore(dstkey, keys);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key将set中的value移除并添加到第二个set中</p>
     *
     * @param srckey 需要移除的
     * @param dstkey 添加的
     * @param member set中的value
     * @return
     */
    public Long smove(String srckey, String dstkey, String member) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.smove(srckey, dstkey, member);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key获取set中value的个数</p>
     *
     * @param key
     * @return
     */
    public Long scard(String key) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.scard(key);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key判断value是否是set中的元素</p>
     *
     * @param key
     * @param member
     * @return
     */
    public Boolean sismember(String key, String member) {
        Jedis jedis = null;
        Boolean res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.sismember(key, member);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key获取set中随机的value,不删除元素</p>
     *
     * @param key
     * @return
     */
    public String srandmember(String key) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.srandmember(key);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key获取set中 count 个随机的value,不删除元素</p>
     *
     * @param key
     * @return
     */
    public List<String> srandmember(String key, int count) {
        Jedis jedis = null;
        List<String> res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.srandmember(key, count);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key获取set中所有的value</p>
     *
     * @param key
     * @return
     */
    public Set<String> smembers(String key) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.smembers(key);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key向zset中添加value,score,其中score就是用来排序的</p>
     * <p>如果该value已经存在则根据score更新元素</p>
     *
     * @param key
     * @param scoreMembers
     * @return
     */
    public Long zadd(String key, Map<String, Double> scoreMembers) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.zadd(key, scoreMembers, ZAddParams.zAddParams());
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key向zset中添加value,score,其中score就是用来排序的</p>
     * <p>如果该value已经存在则根据score更新元素</p>
     *
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Long zadd(String key, double score, String member) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.zadd(key, score, member);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key删除在zset中指定的value</p>
     *
     * @param key
     * @param members 可以使一个string 也可以是一个string数组
     * @return
     */
    public Long zrem(String key, String... members) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.zrem(key, members);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key增加该zset中value的score的值</p>
     *
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Double zincrby(String key, double score, String member) {
        Jedis jedis = null;
        Double res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.zincrby(key, score, member);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key返回zset中value的排名</p>
     * <p>下标从小到大排序</p>
     *
     * @param key
     * @param member
     * @return
     */
    public Long zrank(String key, String member) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.zrank(key, member);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key返回zset中value的排名</p>
     * <p>下标从大到小排序</p>
     *
     * @param key
     * @param member
     * @return
     */
    public Long zrevrank(String key, String member) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.zrevrank(key, member);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key将获取score从start到end中zset的value</p>
     * <p>socre从大到小排序</p>
     * <p>当start为0 end为-1时返回全部</p>
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zrevrange(String key, long start, long end) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key将获取score从start到end中zset的value</p>
     * <p>socre从小到大排序</p>
     * <p>当start为0 end为-1时返回全部</p>
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zrange(String key, long start, long end) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.zrange(key, start, end);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key返回指定score内zset中的value</p>
     *
     * @param key
     * @param max
     * @param min
     * @return
     */
    public Set<String> zrevrangeByScore(String key, String max, String min) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.zrevrangeByScore(key, max, min);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key返回指定score内zset中的value</p>
     *
     * @param key
     * @param max
     * @param min
     * @return
     */
    public Set<String> zrevrangeByScore(String key, double max, double min) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.zrevrangeByScore(key, max, min);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key返回指定score内zset中的value</p>
     *
     * @param key
     * @param max
     * @param min
     * @param offset 偏移量
     * @param count  个数
     * @return
     */
    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.zrevrangeByScore(key, max, min, offset, count);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>返回指定区间内zset中value的数量</p>
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long zcount(String key, String min, String max) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.zcount(key, min, max);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>返回指定区间内zset中value的数量</p>
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long zcount(String key, double min, double max) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.zcount(key, min, max);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key返回zset中的value个数</p>
     *
     * @param key
     * @return
     */
    public Long zcard(String key) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.zcard(key);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key获取zset中value的score值</p>
     *
     * @param key
     * @param member
     * @return
     */
    public Double zscore(String key, String member) {
        Jedis jedis = null;
        Double res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.zscore(key, member);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key删除给定区间内的元素</p>
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zremrangeByRank(String key, long start, long end) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.zremrangeByRank(key, start, end);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key删除指定score内的元素</p>
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zremrangeByScore(String key, double start, double end) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.zremrangeByScore(key, start, end);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>返回满足pattern表达式的所有key</p>
     * <p>keys(*)</p>
     * <p>返回所有的key</p>
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.keys(pattern);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>通过key判断值得类型</p>
     *
     * @param key
     * @return
     */
    public String type(String key) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.type(key);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * 消息推送
     *
     * @param channel
     * @param message
     * @return
     */
    public long publish(String channel, String message) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.publish(channel, message);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * 消息订阅
     *
     * @param jedisPubSub
     * @param patterns
     */
    public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            jedis.psubscribe(jedisPubSub, patterns);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
    }

    /**
     * 阻塞式，移除并返回列表 key 的头元素。
     *
     * @param timeout 阻塞超时时间，单位秒
     * @param key
     * @return
     */
    public String blpop(int timeout, String key) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            List<String> results = jedis.blpop(timeout, key);
            if (results != null && results.size() > 0) {
                res = results.get(1);
            }
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    /**
     * 给key设置过期时间
     *
     * @param key
     * @param seconds 单位秒
     * @return
     */
    public Long expire(final String key, final int seconds) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = JedisUtil.getJedisInstance();
            res = jedis.expire(key, seconds);
        } catch (Exception e) {
            JedisUtil.returnResource(jedis);
            e.printStackTrace();
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return res;
    }

    public Jedis getJedis() {
        Jedis jedis = JedisUtil.getJedisInstance();
        return jedis;
    }

    public void returnJedis(Jedis jedis) {
        JedisUtil.returnResource(jedis);
    }


    public static void main(String[] args) throws Exception {
        //连接池测试
        /*for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
    			RedisUtil redisUtil = new RedisUtil();
				@Override
				public void run() {
					System.out.println("----");
					System.out.println(redisUtil.blpop(100, "jerome"));
				}
			}).start();
    		Thread.sleep(1000);
		}*/
        RedisUtil redisUtil = new RedisUtil();

    	
    	/*System.out.println(redisUtil.lpush("nba", "kobe"));
    	System.out.println(redisUtil.lpush("nba", "james"));
    	System.out.println(redisUtil.lpush("nba", "lin"));
    	System.out.println(redisUtil.lpush("nba", "yaoming"));
    	System.out.println(redisUtil.lpush("nba", "james"));
    	
    	System.out.println(redisUtil.rpop("nba"));
    	System.out.println(redisUtil.rpop("nba"));
    	System.out.println(redisUtil.rpop("nba"));
    	
    	System.out.println(redisUtil.llen("nba"));
    	System.out.println(redisUtil.rpop("nba"));
    	System.out.println(redisUtil.llen("nba"));*/

        //System.out.println(redisUtil.rpop("nba"));
        //String listKey = "nba";
        //redisUtil.del(listKey);
        //redisUtil.rpush(listKey, "姚明");
        //redisUtil.rpush(listKey, "科比");
        //redisUtil.rpush(listKey, "韦德");
        //System.out.println(redisUtil.lrange(listKey, 0, -1));
        //System.out.println(redisUtil.ltrim(listKey, 0, 1000));
        //System.out.println(redisUtil.lrange(listKey, 0, -1));
        //System.out.println(redisUtil.llen(listKey));
        //System.out.println(redisUtil.blpop(10, listKey));

        //2秒后过期
        //redisUtil.expire(listKey, 2);
        //Thread.sleep(2 * 1000);

        //System.out.println(redisUtil.blpop(1, listKey));
        //System.out.println(redisUtil.blpop(1, listKey));

        //String setKey = "DiffPushSet";
        //redisUtil.del(setKey);
    	/*
		System.out.println(redisUtil.zadd(setKey , 1, "1"));
		System.out.println(redisUtil.zadd(setKey , 2, "2"));
		System.out.println(redisUtil.zadd(setKey , 3, "3"));
		System.out.println(redisUtil.zadd(setKey , 4, "4"));
		System.out.println(redisUtil.zrevrange(setKey, 0, 0));
		redisUtil.zrem(setKey, "4");
		System.out.println(redisUtil.zrevrange(setKey, 0, 0));*/

        //System.out.println(redisUtil.zrange(setKey, 0, -1));
    	
    	/*redisUtil.sadd(setKey, "a", "b", "c");
    	System.out.println(redisUtil.sismember(setKey, "a"));
    	System.out.println(redisUtil.srem(setKey, "a"));
    	System.out.println(redisUtil.sismember(setKey, "a"));*/
    	
    	/*System.out.println(redisUtil.del("age"));
    	System.out.println(redisUtil.incrBy("age", 10L));
    	System.out.println(redisUtil.incrBy("age", 10L));
    	System.out.println(redisUtil.get("age"));*/
    	
/*    	Map<String, Double> scoreMembers = new HashMap<String, Double>();
    	scoreMembers.put("jerome", 1d);
    	scoreMembers.put("like", 2d);
    	System.out.println(redisUtil.zadd("testSet", scoreMembers));*/
    	
    	/*String key = "AppUserSwitch_000001";
    	String field = "sdk_type";
    	String value = "1";
    	System.out.println(redisUtil.hset(key, field, value));
    	System.out.println(redisUtil.hget(key, field));
    	Map<String, String> hash = new HashMap<String, String>();
    	hash.put("login_name", "jerome");
    	hash.put("user_name", "jelly");
		System.out.println(redisUtil.hmset(key, hash));
		System.out.println(redisUtil.hgetall(key));
		System.out.println(redisUtil.hdel(key, field));
		System.out.println(redisUtil.hgetall(key));*/

        //setTest();
        sortSetTest();
    }

    private static void setTest() {
        RedisUtil redisUtil = new RedisUtil();

        while (true) {
            long minute = System.currentTimeMillis() / (1000 * 60);
            System.out.println("minute=" + minute);
            //String members = UUID.randomUUID().toString();
            //redisUtil.sadd("online_" + minute, members);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void sortSetTest() {
        RedisUtil redisUtil = new RedisUtil();
        while (true) {
            long score = System.currentTimeMillis() / (1000 * 60);
            System.out.println("score=" + score);
            String setKey = "OnLineSet";
            String members = MD5Utils.getStringMD5(String.valueOf(new Random().nextInt(1000)));
            System.out.println("members=" + members);
            redisUtil.zadd(setKey, score, members);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
