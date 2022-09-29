package spring2.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.context.annotation.Bean;
import spring1.dao.BookDao;

import javax.sql.DataSource;

//@Configuration
public class JDBCConfig {
    /**
     * 添加一个bean，表示当前方法的返回值是一个bean
     *
     * @return 返回一个 DataSource Bean
     */
    @Bean
    public DataSource dataSource(BookDao bookDao) {
        System.out.println("BookDao Address " + bookDao);
        MysqlDataSource ds = new MysqlDataSource();
        ds.setUrl("${jdbc.url}");
        ds.setUser("${jdbc.username}");
        ds.setPassword("${jdbc.password}");
        return ds;
    }
}
