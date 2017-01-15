import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 服务启动程序
 * Created by WZW on 2016/5/30 0030.
 */
@Configuration//配置控制
@EnableAutoConfiguration//启用自动配置
@ComponentScan( basePackages = {"com.yinqiyun.redlichee"})//组件扫描
public class ApplicationMain {
    public static void main(String[] args) throws Exception {
        //启动Spring Boot项目的唯一入口
        SpringApplication.run(ApplicationMain.class, args);
    }

}
