package com.maria.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.*;
import java.time.format.DateTimeFormatter;

@WebServlet (value = "/time")
public class TimeServlet extends HttpServlet {
    private final Instant instant = Instant.now();
    private String offset = "";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=utf-8");
        String timezone = req.getParameter("timezone");

        if (timezone != null) {
            String sign = timezone.substring(3, 4);
            int hoursOffset = Integer.parseInt(timezone.substring(4));

            if (sign.equals("-")) {
                offset = "-" + hoursOffset;
            } else {
                offset = "+" + hoursOffset;
            }
            printResponse(resp);
        } else {
            printResponse(resp);
        }
    }

    private void printResponse(HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        out.write("<h1>Current time:<br>${dateTime}</h1>"
            .replace("${dateTime}", instant.atZone(ZoneId.of("UTC" + offset))
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"))));
        out.close();
    }
}



