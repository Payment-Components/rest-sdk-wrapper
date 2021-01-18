package com.paymentcomponents.libraries.rest.sdk.wrapper.filter;

import com.paymentcomponents.libraries.rest.sdk.wrapper.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

//@Component
public class RequestLogIdFilter implements Filter {

    private final static Logger LOG = LoggerFactory.getLogger(RequestLogIdFilter.class);

    @Override
    public void doFilter(
      ServletRequest request,
      ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
 
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String logId = UUID.randomUUID().toString();
        req.setAttribute(Constants.REQUEST_LOG_ID, logId);
        LOG.info("Request got LogID :{}", logId);
        res.addHeader(Constants.REQUEST_LOG_ID, logId);

        chain.doFilter(request, response);


    }

}