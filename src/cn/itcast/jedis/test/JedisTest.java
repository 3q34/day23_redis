package cn.itcast.jedis.test;

import cn.itcast.jedis.util.JedisPoolUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by cdx on 2019/12/18.
 * desc:
 */
public class JedisTest {
    @Test
    public void testString() {
        //获取连接
        Jedis jedis = new Jedis("localhost", 6379);
        //操作
        jedis.set("username", "lisi");
        //关闭连接
        jedis.close();
    }

    @Test
    public void testString2() {
        //获取连接
        Jedis jedis = new Jedis();
        //操作
        jedis.setex("activecode", 20, "hehe");//设置20秒后删除该添加的键值对
        //关闭连接
        jedis.close();
    }

    @Test
    public void testHash() {
        //获取连接
        Jedis jedis = new Jedis("localhost", 6379);
        //操作
        jedis.hset("myhash", "pwd", "123");
        String pwd = jedis.hget("myhash", "pwd");
        System.out.println(pwd);
        Map<String, String> myhash = jedis.hgetAll("myhash");
        Set<String> strings = myhash.keySet();
        for (String string : strings) {
            System.out.println(myhash.get(string));
        }
        System.out.println(myhash);
        //关闭连接
        jedis.close();
    }

    @Test
    public void testList() {
        //获取连接
        Jedis jedis = new Jedis("localhost", 6379);
        //操作
        jedis.lpush("mylist", "aa");
        jedis.lpush("mylist", "bb");
        jedis.lpush("mylist", "cc");

        List<String> mylist = jedis.lrange("mylist", 0, -1);
        System.out.println(mylist);
        String s = jedis.lpop("mylist");
        System.out.println(s);
        //关闭连接
        jedis.close();
    }

    @Test
    public void testSet() {
        //获取连接
        Jedis jedis = new Jedis("localhost", 6379);
        //操作
        jedis.sadd("myset", "aaaa");
        jedis.sadd("myset", "bbbb");
        Set<String> myset = jedis.smembers("myset");
        System.out.println(myset);
        //关闭连接
        jedis.close();
    }

    @Test
    public void testSortedSet() {
        //获取连接
        Jedis jedis = new Jedis("localhost", 6379);
        //操作
        jedis.zadd("mysort", 20.01, "zhangsan");
        jedis.zadd("mysort", 54.01, "lisi");
        jedis.zadd("mysort", 33.01, "wangwu");


        Set<String> mysort = jedis.zrange("mysort", 0, -1);
        System.out.println(mysort);
        //关闭连接
        jedis.close();
    }

    @Test
    public void testJedisPool() {
        //创建配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(50);
        config.setMaxIdle(10);
        JedisPool pool = new JedisPool();
        Jedis jedis = pool.getResource();
        jedis.set("age", "20");
        jedis.close();//归还到连接池
    }

    @Test
    public void testJedisPoolUtil() {
        //创建配置对象
        Jedis jedis = JedisPoolUtils.getJedis();
        jedis.set("address", "山东");
        String address = jedis.get("address");
        System.out.println(address);
        jedis.close();//归还到连接池
    }

}
