package spring1.dao.impl;

import spring1.dao.UserDao;

public class UserDaoImpl implements UserDao {
    public UserDaoImpl() {
        System.out.println("Creating User Dao object");
    }

    public void save() {
        System.out.println("User dao save ...");
    }
}
