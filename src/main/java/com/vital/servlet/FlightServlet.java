package com.vital.servlet;

import com.vital.dao.FlightDao;
import com.vital.mapper.FlightMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.vital.service.FlightService;
import com.vital.util.JspHelper;
import com.vital.util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.FLIGHTS)
public class FlightServlet extends HttpServlet {

    private final FlightService flightService = new FlightService(new FlightDao(), new FlightMapper());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("flights", flightService.findAll());

        req.getRequestDispatcher(JspHelper.getPath("flights"))
                .forward(req, resp);
    }
}