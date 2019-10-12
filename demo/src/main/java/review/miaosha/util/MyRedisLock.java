package review.miaosha.util;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;
import review.miaosha.config.ConfigUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyRedisLock implements Lock {

    RedisTemplate redisTemplate;
    String resourceName;// 你要锁的资源 如：商品编号
    int timeout;// 锁的时间 单位秒

    public MyRedisLock(RedisTemplate redisTemplate, String resourceName) {
        this.redisTemplate = redisTemplate;
        this.resourceName = resourceName;
    }

    public MyRedisLock(RedisTemplate redisTemplate, String resourceName, int timeout) {
        this.redisTemplate = redisTemplate;
        this.resourceName = resourceName;
        this.timeout = timeout;
    }

    @Override
    public void lock() {//一直要等抢到锁为止
        /** 轮询方式 */
//        while(!tryLock()){
//            //反复尝试，过一段时间在去尝试
//            try{
////                Thread.sleep(100L);//间隔100毫秒
//                TimeUnit.MILLISECONDS.sleep(100L);
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }
//        }

        /** 发布订阅方式 */
        while(!tryLock()){
            //订阅指定的redis主题，接收是否锁的信号
            redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    try {
                        CountDownLatch waiter = new CountDownLatch(1);
                        // subscribe 立马返回，是否订阅完毕.subscribe异步除非
                        connection.subscribe(new MessageListener() {
                            @Override
                            public void onMessage(Message message, byte[] pattern) {
                                // 收到消息，不管结果，立刻再次抢锁
                                waiter.countDown();// 计数器-1
                            }
                        },("release_lock_"+resourceName).getBytes());
                        // 等待有通知，才继续循环
                        waiter.await(timeout,TimeUnit.SECONDS);// 等待技术器为0，在继续执行下面的代码
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    return 0L;
                }
            });
        }
    }
    /** 轮询方式 */
//    @Override
//    public void unlock() {
//        // 删除掉key,资源锁的标记
//        redisTemplate.delete(resourceName);
//    }
    /** 发布订阅方式 */
    @Override
    public void unlock() {
        // 删除掉key,资源锁的标记
        redisTemplate.delete(resourceName);
        // 通过redis发布订阅机制发送一个通知给其他等待的请求
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                Long received = connection.publish(("release_lock_"+resourceName).getBytes(),"value".getBytes());
                return received;
            }
        });
    }
    @Override
    public boolean tryLock() {//尝试获取锁
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
//                System.out.println("进来了？？？");
                String value = "";//随便存一个值，只是加锁而已，跟值没有关系
                // spring-data-redis版本2.0以上  connection.set(key,value,过期时间,命令选项（SET_IF_ABSENT 如果不存在就设置值到redis里面）)
                Boolean result = connection.set(resourceName.getBytes(), value.getBytes(), Expiration.seconds(timeout), RedisStringCommands.SetOption.SET_IF_ABSENT);
//                Boolean result = connection.set("hyy".getBytes(), value.getBytes(), Expiration.seconds(timeout), RedisStringCommands.SetOption.SET_IF_ABSENT);
//                System.out.println(result+" ===");
                return result;
            }
        });
        return false;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public Condition newCondition() {
        return null;
    }
}
