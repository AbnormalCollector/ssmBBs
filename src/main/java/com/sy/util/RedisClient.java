package com.sy.util;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @ClassName RedisClient
 * @Description TODO
 * @Author Administrator
 * @Date: 2021/12/14 11:25
 * @Version 1.0
 */
public class RedisClient {
    static JedisPool pool;

    static {

        JedisPoolConfig config = new JedisPoolConfig();
        //初始化参数
        config.setMaxTotal(200);//池中连接数据最大数
        config.setMaxIdle(50);//池中允许一定空闲的连接数存在
        config.setMaxWaitMillis(1000 * 100);//等待连接的最大时间
        pool = new JedisPool(config, "localhost", 6379);
    }

    /**
     * 获取连接
     *
     * @return
     */
    public static Jedis getJedis() {
        return pool.getResource();
    }

    /**
     * 存储对像或集合
     *
     * @param key
     * @param target
     */
    public static boolean setObject(String key, Object target) {
        Jedis jedis = getJedis();
        try {
            String json = JSON.toJSONString(target);
            String set = jedis.set(key, json);
            if ("ok".equalsIgnoreCase(set)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return false;
    }

    /**
     * 取对像
     *
     * @param key
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T getObject(String key, Class<T> target) {
        Jedis jedis = getJedis();
        try {
            String json = jedis.get(key);
            T t = JSON.parseObject(json, target);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

    /**
     * 取集合
     *
     * @param key
     * @param target
     * @param <T>
     * @return
     */
    public static <T> List<T> getList(String key, Class<T> target) {
        Jedis jedis = getJedis();
        try {
            String json = jedis.get(key);
            List<T> list = JSON.parseArray(json, target);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

    /**
     * 序列化
     */
    public static boolean serialize(String key, Object object) {
        //1.转化为字节数组
        Jedis jedis = getJedis();
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(byteArrayOutputStream);
            oos.writeObject(object);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            //2.保存在redis中
            String set = jedis.set(key.getBytes(), bytes);
            if ("ok".equalsIgnoreCase(set)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                jedis.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return false;

    }

    /**
     * 反序列化
     */
    public static Object deserialize(String key) {
        //1.从redis中取出字节数据
        Jedis jedis = getJedis();
        ObjectInputStream ois = null;
        try {
            //2.把字节数组转化为java对像
            byte[] bytes = jedis.get(key.getBytes());
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            Object object = ois.readObject();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
                jedis.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;

    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public static boolean exsits(String key) {
        Jedis jedis = getJedis();
        try {
            Boolean exists = jedis.exists(key);
            return exists;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return false;
    }

    public static boolean exsits(byte[] key) {
        Jedis jedis = getJedis();
        try {
            Boolean exists = jedis.exists(key);
            return exists;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return false;
    }


    /**
     * 清空缓存
     *
     * @param key
     */
    public static void flush(String key) {
        Jedis jedis = getJedis();
        try {
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    public static void flush(byte[] key) {
        Jedis jedis = getJedis();
        try {
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }


    public static void setDate(String key, int time) {
        Jedis jedis = getJedis();
        try {
            jedis.expire(key, time);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    public static void setDate(byte[] key, int time) {
        Jedis jedis = getJedis();
        try {
            jedis.expire(key, time);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    public static void flushAll() {
        Jedis jedis = getJedis();
        try {
            jedis.flushAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }
}
