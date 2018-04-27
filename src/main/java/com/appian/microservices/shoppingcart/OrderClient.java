package com.appian.microservices.shoppingcart;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("orders")
public interface OrderClient {

  @RequestMapping(method = RequestMethod.GET, value = "/orders")
  String list();

  @RequestMapping(method = RequestMethod.POST, value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
  String newOrder(Cart cart);
}
