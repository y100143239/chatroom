package cn.pistachio.action;

import cn.pistachio.service.UserService;
import cn.pistachio.utils.BaseServlet;
import cn.pistachio.vo.User;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by Alex on 2017/6/15.
 */
public class UserServlet extends BaseServlet {

    public String check(HttpServletRequest req, HttpServletResponse res) throws IOException {
        User existUser = (User) req.getSession().getAttribute("existUser");
        if (existUser == null) {
            res.getWriter().println("1");
        }else{
            res.getWriter().println("2");
        }
        return null;
    }

    public String exit(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession();
        req.getSession().invalidate();
        res.sendRedirect(req.getContextPath() + "/index.jsp");
        return null;
    }


    //send message
    public String sendMessage(HttpServletRequest req, HttpServletResponse res) throws IOException {
        //get parameter
        System.out.println("sendMessage invoke....");
        String from = req.getParameter("from");
        String face = req.getParameter("face");
        String to = req.getParameter("to");
        String color = req.getParameter("color");
        String content = req.getParameter("content");
        String sendTime = new Date().toLocaleString();

        ServletContext application = getServletContext();
        String sourceMessage = (String) application.getAttribute("message");
        //concat message : A said to B:
        sourceMessage += "<font color='blue'><strong>" + from
                + "</strong></font><font color='#CC0000'>" + face
                + "<font>said to <font color='green'{" + to + "}</font>:"
                + "<font color='" + color + "'>" + content + "</font>( "
                +   sendTime + " )<br>";
        application.setAttribute("message", sourceMessage);

        return getMessage(req, res);
    }

    public String getMessage(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String message = (String) getServletContext().getAttribute("message");
        if(message != null){
            res.getWriter().println(message);
        }
        return  null;
    }

    //kick out
    public String kick(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // 1. abtain parameter
        int id = Integer.parseInt(req.getParameter("id"));
        // 2. kick out people: destroy the corresponding sesssion in mapwith the user
        //get the Map named userMap(online list)
        Map<User, HttpSession> userMap = (Map<User, HttpSession>) getServletContext().getAttribute("userMap");

        //get the corresponding session of the user use the id
        User user = new User();
        user.setId(id);
        HttpSession session = userMap.get(user);
        session.invalidate();
        res.sendRedirect(req.getContextPath() + "/main.jsp");
        return null;
    }


    public String login(HttpServletRequest req, HttpServletResponse res) {
        //get data
        Map<String, String[]> map = req.getParameterMap();
        User user = new User();
        //encapsulate data
        try {
            BeanUtils.populate(user, map);
            //invoke Service layer to handle data
            UserService us = new UserService();
            User existUser = us.login(user);
            if (existUser == null) {
                //user login failed
                req.setAttribute("msg", "username or password is incorrect!");
                return "/index.jsp";
            } else {
                //login success
                //destory the former session after the 2nd user login
                req.getSession().invalidate();

                //judge whether the user is already existed in Map, if it is, destroy the session
                Map<User, HttpSession> userMap  =(Map<User, HttpSession>)
                        getServletContext().getAttribute("userMap");

                //estimate whether the user is in Map
                if (userMap.containsKey(existUser)) {
                    //invoke the user in map
                    HttpSession session = userMap.get(existUser);
                    //destroy the session
                    session.invalidate();

                }

                //use listener: HttpSessionBindingListener,takee effect on Java Bean
                req.getSession().setAttribute("existUser", existUser);
                ServletContext application = getServletContext();

                String sourceMessage = "";

                if (null != application.getAttribute("message")) {
                    sourceMessage = application.getAttribute("message").toString();
                }

                sourceMessage += "System Notice: <font color='gray'>"
                        + existUser.getUsername() + " entered the chatroom!</font><br>";
                application.setAttribute("message", sourceMessage);

                res.sendRedirect(req.getContextPath() + "/main.jsp");
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
