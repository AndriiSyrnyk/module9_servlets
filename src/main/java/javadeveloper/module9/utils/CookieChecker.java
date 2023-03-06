package javadeveloper.module9.utils;

import jakarta.servlet.http.Cookie;

public class CookieChecker {
    public static boolean isCookieSet(Cookie[] cookies, String cookiesName) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookiesName))
                    return true;
            }
        }

        return false;
    }

    public static String getCookieValue(Cookie[] cookies, String cookiesName) {
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals(cookiesName))
                return cookie.getValue();
        }

        return "";
    }
}
