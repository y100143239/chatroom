package cn.pistachio.service;

import cn.pistachio.dao.UserDao;
import cn.pistachio.dao.UserDaoImpl;
import cn.pistachio.vo.User;

/**
 * Created by Alex on 2017/6/15.
 */
public class UserService {
    public User login(User user) {
        UserDao dao = new UserDaoImpl();
        return dao.login(user);
    }
}
