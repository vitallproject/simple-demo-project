package com.vital.servlet;

import com.vital.dao.UserDao;
import com.vital.exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.vital.dto.CreateUserDto;
import com.vital.entity.Gender;
import com.vital.mapper.CreateUserMapper;
import com.vital.mapper.UserMapper;
import com.vital.service.ImageService;
import com.vital.service.UserService;
import com.vital.util.JspHelper;
import com.vital.util.UrlPath;
import com.vital.validator.CreateUserValidator;

import java.io.IOException;

@MultipartConfig(fileSizeThreshold = 1024 * 1024)
@WebServlet(UrlPath.REGISTRATION)
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = new UserService(
            new UserDao(),
            new ImageService(),
            new CreateUserMapper(),
            new CreateUserValidator(),
            new UserMapper()
    );

    private static final String NAME = "name";
    private static final String IMAGE = "image";
    private static final String BIRTHDAY = "birthday";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String GENDER = "gender";
    private static final String ERRORS = "errors";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(GENDER, Gender.values());
        req.getRequestDispatcher(JspHelper.getPath("registration"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userDto = CreateUserDto.builder()
                .name(req.getParameter(NAME))
                .image(req.getPart(IMAGE))
                .birthday(req.getParameter(BIRTHDAY))
                .email(req.getParameter(EMAIL))
                .password(req.getParameter(PASSWORD))
                .gender(req.getParameter(GENDER))
                .build();
        try {
            userService.create(userDto);
            resp.sendRedirect(UrlPath.LOGIN);
        } catch (ValidationException exception) {
            req.setAttribute(ERRORS, exception.getErrors());
            doGet(req, resp);
        }
    }
}
