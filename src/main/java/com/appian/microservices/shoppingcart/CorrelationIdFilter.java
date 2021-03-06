package com.appian.microservices.shoppingcart;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class CorrelationIdFilter extends HandlerInterceptorAdapter {

  public static final String CORRELATION_ID = "CORRELATION-ID";

  Logger logger = LoggerFactory.getLogger(CorrelationIdFilter.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) {
    String correlationId = UUID.randomUUID().toString();
    if(request.getHeader(CORRELATION_ID) != null) {
      correlationId = request.getHeader(CORRELATION_ID);
      logger.debug("Found correlation id " + correlationId);
    } else {
      logger.debug("No correlation id found. Creating correlation id " + correlationId);
    }
    ThreadContext.put(CORRELATION_ID, correlationId);
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) {
    ThreadContext.remove(CORRELATION_ID);
    logger.debug("Removed " + CORRELATION_ID + " from thread context.");
  }
}
