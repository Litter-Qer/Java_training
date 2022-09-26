package spring1.apps;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring1.dao.BookDao;

public class App3 {

    public static void main(String[] args) {
        // 获取IoC容器
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring1.config/applicationContext.xml");
        BookDao bookService1 = (BookDao) ctx.getBean("bookDao");

        bookService1.save();
        System.out.println(bookService1);
    }
}
