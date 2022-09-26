package spring2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import spring1.dao.BookDao;
import spring2.service.BookService;

@Component("bookService")
public class BookServiceImpl implements BookService {
    @Autowired
    @Qualifier("bookDao")
    private BookDao bookDao;

    @Override
    public void save() {
        bookDao.save();
        System.out.println("Book Service save  ----- Done");
    }
}
