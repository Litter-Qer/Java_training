package spring1.apps;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring1.dao.BookDao;

import javax.sql.DataSource;

public class App7 {

    public static void main(String[] args) {
        // 获取IoC容器
        ApplicationContext app = new ClassPathXmlApplicationContext("spring1.config/applicationContext.xml");
        DataSource dataSource = (DataSource) app.getBean("dataSource");
        BookDao bookDao = (BookDao) app.getBean("bookDao3");
        bookDao.save();
        System.out.println(dataSource);

//        DataSource dataSource1 = (DataSource) app.getBean("c3po");
//        System.out.println(dataSource1);
    }
}
