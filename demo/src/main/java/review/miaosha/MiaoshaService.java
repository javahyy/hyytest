package review.miaosha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import review.miaosha.config.ConfigUtils;
import review.miaosha.util.JedisUtil;
import review.miaosha.util.MyRedisLock;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MiaoshaService {
//    @Autowired
//    StringRedisTemplate stringRedisTemplate;
//    @Autowired
    DataBaseService dataBaseService = new DataBaseService();

    RedisTemplate  stringRedisTemplate = ConfigUtils.StringRedisTemplate();
//    Lock lock = new ReentrantLock();
    Lock lock = new MyRedisLock(stringRedisTemplate,"goods_code_bike",10);


    /** 分布式锁：描杀具体实现   goodsCode 商品编码 userId 用户ID */
    public boolean miaosha(String goodsCode,final String userId){
        lock.lock();//synchronize是悲观锁，Lock是乐观锁
//        lock.tryLock(10, TimeUnit.MILLISECONDS);//如果没有获取到锁，等待19毫秒后尝试一次
        boolean result = false;
//        Jedis jedis = JedisUtil.getJedis();
        try{
            result = dataBaseService.buy(goodsCode,userId);
            System.out.println("秒杀结果："+Thread.currentThread().getName()+": "+result);
            if(result){// 秒杀成功
                // 更新库存
                // 示例：如果秒杀成功，更新一次库存（库存一半时在缓存里面，供页面快速查询）
                String count = dataBaseService.getCount(goodsCode);
                // 模拟线程运行延时，随机睡眠
                if(Integer.valueOf(count)%2==1){
                    Thread.sleep(new Random().nextInt(500));
                }
//                String set = jedis.set(goodsCode, count);
                stringRedisTemplate.opsForValue().set(goodsCode,count);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return result;
    }


//    Lock lock = new ReentrantLock();
    /** 只适合单台服务器：（多台服务器一样数据不一致）描杀具体实现   goodsCode 商品编码 userId 用户ID */
//    public boolean miaosha(String goodsCode,final String userId){
//        lock.lock();//synchronize是悲观锁，Lock是乐观锁
////        lock.tryLock(10, TimeUnit.MILLISECONDS);//如果没有获取到锁，等待19毫秒后尝试一次
//        boolean result = false;
////        Jedis jedis = JedisUtil.getJedis();
//        try{
//            result = dataBaseService.buy(goodsCode,userId);
//            System.out.println("秒杀结果："+result);
//            if(result){// 秒杀成功
//                // 更新库存
//                // 示例：如果秒杀成功，更新一次库存（库存一半时在缓存里面，供页面快速查询）
//                String count = dataBaseService.getCount(goodsCode);
//                // 模拟线程运行延时，随机睡眠
//                if(Integer.valueOf(count)%2==1){
//                    Thread.sleep(new Random().nextInt(500));
//                }
////                String set = jedis.set(goodsCode, count);
//                stringRedisTemplate.opsForValue().set(goodsCode,count);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            lock.unlock();
//        }
//        return result;
//    }


    /** 数据不一致：描杀具体实现   goodsCode 商品编码 userId 用户ID */
//    public boolean miaosha01(String goodsCode,final String userId){
//        boolean result = false;
////        Jedis jedis = JedisUtil.getJedis();
//        RedisTemplate  stringRedisTemplate = ConfigUtils.StringRedisTemplate();
//        try{
//            result = dataBaseService.buy(goodsCode,userId);
//            System.out.println("秒杀结果："+result);
//            if(result){// 秒杀成功
//                // 更新库存
//                // 示例：如果秒杀成功，更新一次库存（库存一半时在缓存里面，供页面快速查询）
//                String count = dataBaseService.getCount(goodsCode);
//                // 模拟线程运行延时，随机睡眠
//                if(Integer.valueOf(count)%2==1){
//                    Thread.sleep(new Random().nextInt(500));
//                }
////                String set = jedis.set(goodsCode, count);
//                stringRedisTemplate.opsForValue().set(goodsCode,count);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return result;
//    }

    public void test(){
//        lock.tryLock();
        lock.lock();
    }
    public static void main(String[] args) {
//       new MiaoshaService().test();
//       new MiaoshaService().miaosha("bike",null);
        new MiaoshaService().stringRedisTemplate.opsForValue().set("hyy","123");
    }
}
