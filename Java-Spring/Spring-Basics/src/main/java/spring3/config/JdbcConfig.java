package spring3.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        MysqlDataSource ds = new MysqlDataSource();
        ds.setUrl("${jdbc.url}");
        ds.setUser("${jdbc.username}");
        ds.setPassword("${jdbc.password}");
        return ds;
    }
}
