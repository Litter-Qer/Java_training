package spring2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring2.config.SpringConfig;
import spring2.service.BookService;

import javax.sql.DataSource;

public class AppForAnnot {
    public static void main(String[] args) {
        ApplicationContext app = new AnnotationConfigApplicationContext(SpringConfig.class);
        DataSource ds = (DataSource) app.getBean("dataSource");
        System.out.println(ds);

        BookService bookService = app.getBean(BookService.class);
        bookService.save();
        System.out.println(bookService);
    }
}
