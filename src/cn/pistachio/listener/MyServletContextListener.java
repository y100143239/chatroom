package cn.pistachio.listener;

import cn.pistachio.vo.User;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * listen ServletContext's creating and destroying
 * Created by Alex on 2017/6/15.
 */
public class MyServletContextListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Map<User, HttpSession> userMap = new HashMap<User, HttpSession>();
        servletContextEvent.getServletContext().setAttribute("userMap", userMap);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
