package com.maria.servlets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.TimeZone;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        String timezone = req.getParameter("timezone");

        if (timezone != null && !isValidTimeZone(timezone)) {
            resp.setContentType("text/html; charset=utf-8");
            resp.setStatus(400);
            resp.getWriter().write("<h1>Invalid timezone!</h1>");
        } else {
            chain.doFilter(req, resp);
        }
    }

    private static boolean isValidTimeZone(String timezone) {
        if (timezone.startsWith("UTC")) {
            try {
                int offsetHours = Integer.parseInt(timezone.substring(4));
                long offsetMillis = (long) offsetHours * 60 * 60 * 1000;
                String[] availableIDs = TimeZone.getAvailableIDs((int) offsetMillis);
                return Arrays.asList(availableIDs).contains("Etc/GMT-" + offsetHours);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
}

