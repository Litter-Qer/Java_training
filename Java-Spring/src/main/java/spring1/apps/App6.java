package spring1.apps;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring1.dao.service.BookService;

public class App6 {

    public static void main(String[] args) {
        // 获取IoC容器
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring1.config/applicationContext.xml");
        BookService bookService = (BookService) ctx.getBean("bookDao");
        bookService.save();
    }
}
