package com.emi.shiro.t15.component.filter;

import com.emi.shiro.t15.common.Constant;
import com.emi.shiro.t15.repository.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by emi on 2017/2/21.
 */
@Component("userFilter2")
public class RequestUserFilter2 extends PathMatchingFilter {

    Logger logger = LoggerFactory.getLogger(RequestUserFilter.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        logger.info(" ----------------------------- shiro filter -------------------------------------");
        String username = ((HttpServletRequest)request).getHeader("username");
        request.setAttribute(Constant.CURRENT_USER, userRepository.findByUsername(username));
        return true;
    }
}
