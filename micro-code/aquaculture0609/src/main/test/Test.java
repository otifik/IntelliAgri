import com.jit.aquaculture.a.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class Test {

   private static RedisTemplate<String, Object> redisTemplate = SpringUtils.getBean("redisTemplate");


    @org.junit.Test
    public void testRedis(){
        System.out.println(redisTemplate);
    }
}
