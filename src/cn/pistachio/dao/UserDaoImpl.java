package cn.pistachio.dao;

import cn.pistachio.utils.JDBCUtils;
import cn.pistachio.vo.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by Alex on 207/6/15.
 */
public class UserDaoImpl implements UserDao{

    @Override
    public User login(User user) {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = " select * from user where username = ? and password = ? ";
        User existUser;
        try{
            existUser = queryRunner.query(sql, new BeanHandler<User>(User.class), user.getUsername(), user.getPassword());
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("User login failed！");
        }
        return existUser;
    }
}
