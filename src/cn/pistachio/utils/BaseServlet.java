package cn.pistachio.utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

/**
 * Created by Alex on 2017/6/9.
 */
public class BaseServlet extends HttpServlet {

    protected void service(HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException, ServletException {
        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=utf-8");

        //example: http://localhost:8080/demo/xxx?method=login
        String methodName = req.getParameter("method");

        if (methodName == null || methodName.isEmpty()) {
            methodName = "execute";
        }

        Class c = this.getClass();
        try {
            Method m = c.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);

            //reflect target method
            String result = (String) m.invoke(this, req, res);
            if (result != null && !result.isEmpty()) {
                req.getRequestDispatcher(result).forward(req, res);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}
