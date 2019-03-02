package review.miaosha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import review.miaosha.util.JedisUtil;

import java.util.Random;

@Component
public class MiaoshaService {
//    @Autowired
//    StringRedisTemplate stringRedisTemplate;
//    @Autowired
    DataBaseService dataBaseService = new DataBaseService();
    /** 描杀具体实现   goodsCode 商品编码 userId 用户ID */
    public boolean miaosha(String goodsCode,final String userId){
        boolean result = false;
        Jedis jedis = JedisUtil.getJedis();
        try{
            result = dataBaseService.buy(goodsCode,userId);
            System.out.println("秒杀结果："+result);
            if(result){// 秒杀成功
                // 更新库存
                // 示例：如果秒杀成功，更新一次库存（库存一半时在缓存里面，供页面快速查询）
                String count = dataBaseService.getCount(goodsCode);
                // 模拟线程运行延时，随机睡眠
                if(Integer.valueOf(count)%2==1){
                    Thread.sleep(new Random().nextInt(500));
                }
                String set = jedis.set(goodsCode, count);
//                stringRedisTemplate.opsForValue().set(goodsCode,count);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
