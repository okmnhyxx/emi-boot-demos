package com.emi.shiro.t15.component.filter;


import com.emi.shiro.t15.common.Constant;
import com.emi.shiro.t15.configs.ShiroConfig;
import com.emi.shiro.t15.repository.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by emi on 2017/2/20.
 */
@WebFilter(filterName = "userFilter", urlPatterns = "/api/*")
public class RequestUserFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(RequestUserFilter.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShiroConfig shiroConfig;

//    @Override
//    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
//
//        String username = (String)SecurityUtils.getSubject().getPrincipal();
//        request.setAttribute(Constant.CURRENT_USER, userRepository.findByUsername(username));
//        return true;
//    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("---------------------------- init filter ------------------------------");
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


//        String username = (String) SecurityUtils.getSubject().getPrincipal();
//        servletRequest.setAttribute(Constant.CURRENT_USER, userRepository.findByUsername(username));
//        filterChain.doFilter(servletRequest, servletResponse);

    }



    @Override
    public void destroy() {
        logger.info("---------------------------- destroy filter ------------------------------");
    }
}
