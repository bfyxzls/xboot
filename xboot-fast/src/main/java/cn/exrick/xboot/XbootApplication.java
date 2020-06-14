package cn.exrick.xboot;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Exrickx
 */
@SpringBootApplication
//启用缓存
@EnableCaching
//启用异步
@EnableAsync
@EnableAdminServer
public class XbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(XbootApplication.class, args);
    }
}
