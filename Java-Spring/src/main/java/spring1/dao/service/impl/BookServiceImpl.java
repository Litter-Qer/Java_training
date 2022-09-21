package spring1.dao.service.impl;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import spring1.dao.BookDao;
import spring1.dao.service.BookService;

public class BookServiceImpl implements BookService, InitializingBean, DisposableBean {
    private BookDao bookDao;

    public void save() {
        System.out.println("book service save ...");
        bookDao.save();
    }

    public void setBookDao(BookDao bookDao) {
        System.out.println("Book Service Set ...");
        this.bookDao = bookDao;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("book service destroying the bean");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("book service init");
    }
}
