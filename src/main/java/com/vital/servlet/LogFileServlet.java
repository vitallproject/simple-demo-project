package com.vital.servlet;

import com.vital.parser.ActivityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.vital.parser.LogFileParser;
import com.vital.parser.StatisticReportGenerator;
import com.vital.parser.TimeReportGenerator;
import com.vital.service.LogFileService;
import com.vital.util.JspHelper;
import com.vital.util.UrlPath;

import java.io.IOException;

@MultipartConfig
@WebServlet(UrlPath.LOG_FILE)
public class LogFileServlet extends HttpServlet {

    private final LogFileService logFileService = new LogFileService(
            new LogFileParser(),
            new TimeReportGenerator(new ActivityUtil()),
            new StatisticReportGenerator(new ActivityUtil())
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("log"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var logFile = req.getPart("log");
        var report = logFileService.process(logFile);
        req.getSession().setAttribute("report", report);
        resp.sendRedirect(UrlPath.REPORT);
    }
}
