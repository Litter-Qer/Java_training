package spring2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring1.dao.BookDao;
import spring2.service.BookService;

public class App {
    public static void main(String[] args) {
        ApplicationContext app = new ClassPathXmlApplicationContext("spring1.config/applicationContext2.xml");
        BookDao bookDao = app.getBean(BookDao.class);
        System.out.println(bookDao);

        BookService bookService = app.getBean(BookService.class);
        System.out.println(bookService);
    }
}
