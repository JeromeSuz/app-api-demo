package com.example.util.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Jedis工具类
 *
 * @author jerome
 * @date 2017/3/7 9:32
 */
public class JedisUtil {

    /**
     * IP
     **/
    private final static String REDIS_IP_KEY = "redis.ip";
    /**
     * Port
     **/
    private final static String REDIS_PORT_KEY = "redis.port";
    /**
     * 数据库索引，默认使用0号数据库
     **/
    private final static String REDIS_DB_INDEX_KEY = "redis.db_index";
    /**
     * 可用连接实例的最大数目，默认值为8
     * 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
     **/
    private final static String REDIS_MAXACTIVE_KEY = "redis.pool.maxActive";
    /**
     * 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8
     **/
    private final static String REDIS_MAXIDLE_KEY = "redis.pool.maxIdle";
    /**
     * 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
     **/
    private final static String REDIS_MAXWAIT_KEY = "redis.pool.maxWait";
    /**
     * 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
     **/
    private final static String REDIS_TESTONBORROW_KEY = "redis.pool.testOnBorrow";
    /**
     * 当调用return Object方法时，是否进行有效性检查
     **/
    private final static String REDIS_TESTONRETURN_KEY = "redis.pool.testOnReturn";
    /**
     * 超时时间
     **/
    public final static String REDIS_TIMEOUT_KEY = "redis.pool.timeout";

    private static int DEFAULT_DB_INDEX = 0;

    private static JedisPool jedisPool = null;

    private static final Logger LOG = LoggerFactory.getLogger(JedisUtil.class.getName());

    private JedisUtil() {
    }

    private static void initialPool() {
        try {
            Locale local = Locale.getDefault();
            ResourceBundle bundle = ResourceBundle.getBundle("redis", local, JedisUtil.class.getClassLoader());
            if (bundle == null) {
                throw new IllegalArgumentException("[redis.properties] is not found!");
            }
            // 创建jedis池配置实例  
            JedisPoolConfig config = new JedisPoolConfig();
            // 设置池配置项值
            String address = bundle.getString(REDIS_IP_KEY);
            int port = Integer.valueOf(bundle.getString(REDIS_PORT_KEY));
            LOG.info("address =" + address + " port=" + port);

            String strDbIndex = bundle.getString(REDIS_DB_INDEX_KEY);
            LOG.info("strDbIndex =" + strDbIndex);
            if (strDbIndex != null) {
                DEFAULT_DB_INDEX = Integer.valueOf(strDbIndex);
            }

            String strMaxActive = bundle.getString(REDIS_MAXACTIVE_KEY);
            LOG.info("strMaxActive =" + strMaxActive);
            if (strMaxActive != null) {
                config.setMaxTotal(Integer.valueOf(strMaxActive));
            }

            String strMaxIdle = bundle.getString(REDIS_MAXIDLE_KEY);
            LOG.info("strMaxIdle =" + strMaxIdle);
            if (strMaxIdle != null) {
                config.setMaxIdle(Integer.valueOf(strMaxIdle));
            }

            String strMaxWait = bundle.getString(REDIS_MAXWAIT_KEY);
            LOG.info("strMaxWait =" + strMaxWait);
            if (strMaxWait != null) {
                config.setMaxWaitMillis(Long.valueOf(strMaxWait));
            }

            String strTestOnBorrow = bundle.getString(REDIS_TESTONBORROW_KEY);
            LOG.info("strTestOnBorrow =" + strTestOnBorrow);
            if (strTestOnBorrow != null) {
                config.setTestOnBorrow(Boolean.valueOf(strTestOnBorrow));
            }

            String strTestOnReturn = bundle.getString(REDIS_TESTONRETURN_KEY);
            LOG.info("strTestOnReturn =" + strTestOnReturn);
            if (strTestOnReturn != null) {
                config.setTestOnReturn(Boolean.valueOf(strTestOnReturn));
            }

            String strTimeout = bundle.getString(REDIS_TIMEOUT_KEY);
            LOG.info("strTimeout =" + strTimeout);
            int timeout = 2000;
            if (strTimeout != null) {
                timeout = Integer.valueOf(strTimeout);
            }
            // 根据配置实例化jedis池  
            jedisPool = new JedisPool(config, address, port, timeout);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public synchronized static Jedis getJedisInstance() {
        if (jedisPool == null) {
            initialPool();
        }
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                resource.select(DEFAULT_DB_INDEX);
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回连接池
     *
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}

