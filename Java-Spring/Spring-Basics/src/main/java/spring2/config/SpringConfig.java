package spring2.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
@ComponentScan("spring2")
@Import(JDBCConfig.class)
@PropertySource("classpath:jdbc.properties")
public class SpringConfig {

}
