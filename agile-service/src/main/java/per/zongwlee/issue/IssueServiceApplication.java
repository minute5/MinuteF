package per.zongwlee.issue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("io.choerodon")
@EnableEurekaClient
public class IssueServiceApplication {

    public static void main(String[] args){
        SpringApplication.run(IssueServiceApplication.class, args);
    }

}
