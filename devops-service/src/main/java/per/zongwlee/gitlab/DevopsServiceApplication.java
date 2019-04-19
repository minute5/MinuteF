package per.zongwlee.gitlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients("per.zongwlee")
public class DevopsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevopsServiceApplication.class, args);
    }
}
