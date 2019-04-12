package per.zongwlee.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.retry.annotation.EnableRetry;
import per.zongwlee.gateway.filter.AccessFilter;

@EnableFeignClients("per.zongwlee.gateway")
@EnableRetry
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }
}
