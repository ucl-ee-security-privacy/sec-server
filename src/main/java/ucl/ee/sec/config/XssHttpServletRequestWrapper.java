package ucl.ee.sec.config;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import jakarta.servlet.http.*;

@Component
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        return HtmlUtils.htmlEscape(value);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return HtmlUtils.htmlEscape(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        for (int i = 0; i < values.length; i++) {
            values[i] = HtmlUtils.htmlEscape(values[i]);
        }
        return values;
    }

}
