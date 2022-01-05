package com.vital.servlet;

import com.vital.dao.FlightDao;
import com.vital.dao.TicketDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.vital.mapper.TicketMapper;
import com.vital.service.TicketService;
import com.vital.util.JspHelper;
import com.vital.util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.TICKETS)
public class TicketServlet extends HttpServlet {

    private final TicketService ticketService = new TicketService(new TicketDao(new FlightDao()), new TicketMapper());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var flightId = Long.valueOf(req.getParameter("flightId"));
        req.setAttribute("tickets", ticketService.findAllByFlightId(flightId));
        req.getRequestDispatcher(JspHelper.getPath("tickets"))
                .forward(req, resp);
    }
}
