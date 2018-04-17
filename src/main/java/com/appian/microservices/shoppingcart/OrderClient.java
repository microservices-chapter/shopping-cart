package com.appian.microservices.shoppingcart;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("orders")
public interface OrderClient {

  @RequestMapping(value = "/orders")
  String list();

  // TODO: waiting on aravind to add create
}
