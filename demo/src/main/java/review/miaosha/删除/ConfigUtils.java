package review.miaosha.删除;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class ConfigUtils {

//    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        //jackson将java对象转换成json对象。
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    // 配置文件参考： https://www.cnblogs.com/wojiaochuichui/p/8662005.html
    // https://blog.csdn.net/hanjun0612/article/details/78131333 这里改成代码版本
//    @Bean
    public JedisConnectionFactory getRedisConnectionFactory(){
        // 连接池工厂
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        // 1） 设置 连接池配置
        // 1-1)构造连接池配置
        JedisPoolConfig config = new JedisPoolConfig();
        //修改最大连接数
        config.setMaxTotal(20);

        // 1-2)连接池配置
        JedisPool pool = new JedisPool(config,"192.168.1.102",6379);
        //获得jedis对象
//        Jedis jedis = pool.getResource();  这个就可以直接操作redis了
        jedisConnectionFactory.setHostName("192.168.1.102");
        jedisConnectionFactory.setPort(6379);
//        jedisConnectionFactory.setPassword();
        jedisConnectionFactory.setPoolConfig(config);
        return jedisConnectionFactory;
    }

//    @Bean
//    public <T> RedisTemplate<String, T> redisTemplateKeyString(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//    /**配置其他类型的redisTemplate***/
//    @Bean
//    public RedisTemplate<Object, Object> redisTemplateKeyObject(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }

}
