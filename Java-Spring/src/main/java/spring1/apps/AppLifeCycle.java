package spring1.apps;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring1.dao.service.BookService;

public class AppLifeCycle {

    public static void main(String[] args) {
        // 获取IoC容器
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
//        app.registerShutdownHook();
        BookService bookService = (BookService) app.getBean("bookService");
        bookService.save();
        app.close();
    }
}
