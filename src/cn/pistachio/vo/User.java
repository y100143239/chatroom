package cn.pistachio.vo;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.Map;

/**
 * Created by Alex on 2017/6/15.
 */
public class User implements HttpSessionBindingListener {
    private int id;
    private String username;
    private String password;
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    //bind the java bean with session
    //session.setAttribute();
    @Override
    public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("entered...");
        HttpSession session = httpSessionBindingEvent.getSession();

        Map<User, HttpSession> userMap = (Map<User, HttpSession>)
                session.getServletContext().getAttribute("userMap");
        userMap.put(this, session);
    }

    //unbind the java bean with session
    @Override
    public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("exited.....");
        HttpSession session = httpSessionBindingEvent.getSession();

        Map<User, HttpSession> userMap = (Map<User, HttpSession>)
                session.getServletContext().getAttribute("userMap");
        userMap.remove(this);
    }
}
