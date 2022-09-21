package spring1.apps;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring1.dao.service.BookService;

public class App {

    public static void main(String[] args) {
        // 获取IoC容器
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookService bookService1 = (BookService) ctx.getBean("bookService");
        BookService bookService2 = (BookService) ctx.getBean("bookService");

        System.out.println(bookService1);
        System.out.println(bookService2);
        System.out.println(bookService1.equals(bookService2));
//        bookService.save();
    }
}
