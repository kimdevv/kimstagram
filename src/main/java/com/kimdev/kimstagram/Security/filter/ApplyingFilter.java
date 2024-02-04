package com.kimdev.kimstagram.Security.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ApplyingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if(req.getMethod().equals("GET")) { // GET 요청이 들어올 경우 동작
            String tokenHeader = req.getHeader(("Authorization"));
            if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) { // 로그인되지 않았다면
                ((HttpServletResponse) response).sendRedirect("/");
            } else { // 로그인 되었다면
                chain.doFilter(req, res); // 필터를 타게 한다.
            }
        } else {
            chain.doFilter(req, res);
        }
    }
}
