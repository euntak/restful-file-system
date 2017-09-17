package io.euntak.resolver;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class CustomMultipartResolver extends CommonsMultipartResolver {
    private static final String MULTIPART = "multipart/";

    public static final boolean isMultipartContent(HttpServletRequest request) {

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        final String method = request.getMethod().toLowerCase();
        if (! method.equals("post") && ! method.equals("put")) {
            return false;
        }
        String contentType = request.getContentType();
        if (contentType == null) {
            return false;
        }
        return contentType.toLowerCase().startsWith(MULTIPART);
    }

    @Override
    public boolean isMultipart(HttpServletRequest request) {

        return request != null && isMultipartContent(request);
    }
}
