package per.zongwlee.gateway.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.session.data.redis.RedisFlushMode;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({ChoerodonRedisHttpSessionConfiguration.class})
@Configuration
public @interface EnableRedisHttpSession {
    int maxInactiveIntervalInSeconds() default 1800;

    String redisNamespace() default "";

    RedisFlushMode redisFlushMode() default RedisFlushMode.ON_SAVE;
}
