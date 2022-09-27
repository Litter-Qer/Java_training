package spring2.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring1.dao.BookDao;

import javax.sql.DataSource;
import java.awt.print.Book;

//@Configuration
public class JDBCConfig {
    /**
     * 添加一个bean，表示当前方法的返回值是一个bean
     * @return 返回一个 DataSource Bean
     */
    @Bean
    public DataSource dataSource(BookDao bookDao) {
        System.out.println("BookDao Address " + bookDao);
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("${jdbc.driver}");
        ds.setUrl("${jdbc.url}");
        ds.setUsername("${jdbc.username}");
        ds.setPassword("${jdbc.password}");
        return ds;
    }
}
