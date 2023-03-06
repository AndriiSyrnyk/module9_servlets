package javadeveloper.module9.servlets;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javadeveloper.module9.utils.ZoneOffsetValidator;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req,
                            HttpServletResponse resp,
                            FilterChain chain) throws IOException, ServletException {
        String parameterValue = req.getParameter("timezone");

        if (parameterValue != null) {
            parameterValue = URLDecoder
                    .decode(parameterValue, StandardCharsets.UTF_8)
                    .replace(' ', '+');
        }

        if (parameterValue == null ||
                ZoneOffsetValidator.isZoneOffsetValid(parameterValue)) {
            chain.doFilter(req, resp);
        } else {
            resp.setStatus(400);
            resp.setContentType("text/html; charset=utf-8");
            resp.getWriter().write("<h1>Invalid timezone</h1>");
            resp.getWriter().close();
        }
    }
}
