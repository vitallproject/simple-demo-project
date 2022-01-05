package com.vital.servlet;

import com.vital.dao.UserDao;
import com.vital.dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.SneakyThrows;
import com.vital.mapper.CreateUserMapper;
import com.vital.mapper.UserMapper;
import com.vital.service.ImageService;
import com.vital.service.UserService;
import com.vital.util.JspHelper;
import com.vital.util.UrlPath;
import com.vital.validator.CreateUserValidator;

import java.io.IOException;
import java.util.Locale;

@WebServlet(UrlPath.LOGIN)
public class LoginServlet extends HttpServlet {

    private static final String EMAIL = "email";

    private final UserService userService = new UserService(
            new UserDao(),
            new ImageService(),
            new CreateUserMapper(),
            new CreateUserValidator(),
            new UserMapper()
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("lang", req.getLocale());
        req.getRequestDispatcher(JspHelper.getPath("login"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        userService.login(req.getParameter(EMAIL), req.getParameter("password"))
                .ifPresentOrElse(
                        user -> onLoginSuccess(user, req, resp),
                        () -> onLoginFail(req, resp)
                );
    }


    @SneakyThrows
    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect(UrlPath.LOGIN + "?error&email=" + req.getParameter(EMAIL));
    }

    @SneakyThrows
    private void onLoginSuccess(UserDto user, HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute("user", user);
        resp.sendRedirect(UrlPath.OPTIONS);
    }
}
