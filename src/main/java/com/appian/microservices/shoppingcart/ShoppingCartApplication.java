package com.appian.microservices.shoppingcart;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import feign.Feign;

/**
 * Shopping cart application.
 *
 * @author touré
 */
@SpringBootApplication
@RestController
@EnableFeignClients
public class ShoppingCartApplication extends WebMvcConfigurerAdapter {

  // TODO: Feign https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html
  // Interface says I am going against Aravinds API
  @Autowired
  private CorrelationIdFilter correlationIdFilter;

  private static final Logger LOG = LoggerFactory.getLogger(ShoppingCartApplication.class);
  private static OrderClient orderClient;

  private List<Cart> carts;
  public ShoppingCartApplication() {
    this.carts = new ArrayList<Cart>();
    carts.add(new Cart("ellie"));
  }

  @RequestMapping(value = "/{userId}/items")
  public String list(@PathVariable String userId) {
    return getCartByUserId(userId).getItems().toString();
  }

  @PostMapping(value = "/{userId}/add")
  public ResponseEntity<Void> add(@PathVariable String userId, @RequestBody Item input) {
    Item item = new Item(input.getUuid(), input.getQuantity());
    // TODO: Check that there is enough of the item to add to the cart
    getCartByUserId(userId).addItem(item);
    return ResponseEntity.ok().build();
  }

  @PostMapping(value = "/{userId}/remove")
  public ResponseEntity<Void> remove(@PathVariable String userId, @RequestBody Item input) {
    getCartByUserId(userId).removeItem(input.getUuid());
    return ResponseEntity.ok().build();
  }

  @Override
  public void addInterceptors(InterceptorRegistry interceptorRegistry) {
    interceptorRegistry.addInterceptor(correlationIdFilter);
  }

  @RequestMapping(value = "order")
  public String placeOrder() {
    return orderClient.list();
  }
  // Functions to add
  // Update Quantity
  //

  public Cart getCartByUserId(String userId) {
    return carts.stream().filter(cart -> cart.getUserId().equals(userId)).findFirst().get();
  }

  public static void main(String[] args) {
    orderClient = Feign.builder()
        .target(OrderClient.class, "http://localhost:8080/order");
    ApplicationContext applicationContext = SpringApplication.run(ShoppingCartApplication.class, args);
  }
}
