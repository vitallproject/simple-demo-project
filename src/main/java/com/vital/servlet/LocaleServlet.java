package com.vital.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.vital.util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.LOCALE)
public class LocaleServlet extends HttpServlet {

    private static final String LANGUAGE = "lang";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var language = req.getParameter(LANGUAGE);
        req.getSession().setAttribute(LANGUAGE, language);

        var prevPage = req.getHeader("referer");
        var page = prevPage != null ? prevPage : UrlPath.LOGIN;

        resp.sendRedirect(page);
    }
}