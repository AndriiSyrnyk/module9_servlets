package javadeveloper.module9.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javadeveloper.module9.utils.CookieChecker;
import javadeveloper.module9.utils.FormattedZonedDateTime;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@WebServlet("/time")
public class ThymeleafTimeController extends HttpServlet {
    private TemplateEngine engine;
    @Override
    public void init(ServletConfig config) throws ServletException {
        engine = new TemplateEngine();
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("C:\\Users\\38098\\IdeaProjects\\GOITJavaDeveloper\\Module9Servlets\\templates\\");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currentFormattedZonedDateTime;
        String parameterName = "timezone";
        String cookieName = "lastTimezone";
        boolean isLastTimezoneCookieSet = CookieChecker
                .isCookieSet(req.getCookies(), cookieName);
        String cookieValue = "";

        if (isLastTimezoneCookieSet) {
            cookieValue = CookieChecker.getCookieValue(req.getCookies(), cookieName);
            currentFormattedZonedDateTime = FormattedZonedDateTime
                    .getCurrentFormattedZonedDateTime(cookieValue);
        }
        else {
            if (req.getParameter(parameterName) != null) {
                String timeZone = URLDecoder
                        .decode(req.getParameter(parameterName), StandardCharsets.UTF_8)
                        .replace(' ', '+');
                currentFormattedZonedDateTime = FormattedZonedDateTime
                        .getCurrentFormattedZonedDateTime(timeZone);
                resp.addCookie(new Cookie(cookieName, timeZone));
            } else {
                currentFormattedZonedDateTime = FormattedZonedDateTime
                        .getCurrentFormattedZonedDateTime();
            }
        }

        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("currentTime", currentFormattedZonedDateTime)
        );

        resp.setContentType("text/html");
        engine.process("time", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
