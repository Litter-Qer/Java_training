package spring2.config;

import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
@ComponentScan("spring2")
@Import(JDBCConfig.class)
@PropertySource("classpath:jdbc.properties")
public class SpringConfig {
}
