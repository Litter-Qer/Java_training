package spring2.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import spring1.dao.BookDao;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Repository("bookDao")
public class BookDaoImpl implements BookDao {

    @Value("${name}")
    private String name;

    @Override
    public void save() {
        System.out.println(name);
        System.out.println("Book Dao save  ----- Done");
    }

    @PostConstruct
    public void init() {
        System.out.println("Init  ...");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("destroy ...");
    }

}
