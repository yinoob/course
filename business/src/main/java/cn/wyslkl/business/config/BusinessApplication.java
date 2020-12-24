package cn.wyslkl.business.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@EnableEurekaClient
@SpringBootApplication
@ComponentScan("cn.wyslkl")
@MapperScan("cn.wyslkl.server.mapper")
public class BusinessApplication {

    private static final Logger LOG= LoggerFactory.getLogger(BusinessApplication.class);

    public static void main(String[] args){
        SpringApplication app=new SpringApplication(BusinessApplication.class);
        Environment env=app.run(args).getEnvironment();
        LOG.info("启动成功！");
        LOG.info("Business地址: \thttp://localhost:9001");

    }
}
