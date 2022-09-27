package spring3.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring1.dao.BookDao;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {
    /**
     * 添加一个bean，表示当前方法的返回值是一个bean
     *
     * @return 返回一个 DataSource Bean
     */
    @Bean
    public DataSource dataSource() {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("${jdbc.driver}");
        ds.setUrl("${jdbc.url}");
        ds.setUsername("${jdbc.username}");
        ds.setPassword("${jdbc.password}");
        return ds;
    }
}
