package spring1.factory;

import org.springframework.beans.factory.FactoryBean;
import spring1.dao.UserDao;
import spring1.dao.impl.UserDaoImpl;

public class UserDaoFactoryBean implements FactoryBean<UserDao> {
    @Override
    public UserDao getObject() throws Exception {
        return new UserDaoImpl();
    }

    @Override
    public Class<?> getObjectType() {
        return UserDao.class;
    }
}
