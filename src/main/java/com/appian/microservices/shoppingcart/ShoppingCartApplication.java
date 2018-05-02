package com.appian.microservices.shoppingcart;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

/**
 * Shopping cart application.
 *
 * @author touré
 */
@SpringBootApplication
@RestController
@EnableFeignClients
public class ShoppingCartApplication extends WebMvcConfigurerAdapter {

  @Autowired
  private CorrelationIdFilter correlationIdFilter;

  private static final Logger LOG = LoggerFactory.getLogger(ShoppingCartApplication.class);
  private static OrderClient orderClient;

  private List<Cart> carts;
  public ShoppingCartApplication() {
    this.carts = new ArrayList<Cart>();
  }

  @RequestMapping(method = RequestMethod.GET, value = "/create/{userId}")
  public ResponseEntity<Void> create(@PathVariable String userId) {
    carts.add(new Cart(userId));
    return ResponseEntity.ok().build();
  }

  @RequestMapping(method = RequestMethod.GET, value = "/{userId}/items")
  public String list(@PathVariable String userId) {
    return getCartByUserId(userId).getItems().toString();
  }

  @RequestMapping(method = RequestMethod.POST, value = "/{userId}/add", consumes = "application/json")
  public ResponseEntity<Void> add(@PathVariable String userId, @RequestBody Item input) {
    Item item = new Item(input.getUuid(), input.getQuantity());
    // TODO: Check that there is enough of the item to add to the cart
    getCartByUserId(userId).addItem(item);
    return ResponseEntity.ok().build();
  }

  @RequestMapping(method = RequestMethod.POST, value = "/{userId}/remove", consumes = "application/json")
  public ResponseEntity<Void> remove(@PathVariable String userId, @RequestBody Item input) {
    getCartByUserId(userId).removeItem(input.getUuid());
    return ResponseEntity.ok().build();
  }

  @Override
  public void addInterceptors(InterceptorRegistry interceptorRegistry) {
    interceptorRegistry.addInterceptor(correlationIdFilter);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/{userId}/order")
  public String placeOrder(@PathVariable String userId) {
    Cart cart = getCartByUserId(userId);
    String orderId = orderClient.newOrder(cart);
    cart.emptyCart();
    return "You just placed an order with number: " + orderId;
  }

  public Cart getCartByUserId(String userId) {
    return carts.stream().filter(cart -> cart.getUserId().equals(userId)).findFirst().get();
  }

  public static void main(String[] args) {
    orderClient = Feign.builder()
        .contract(new SpringMvcContract())
        .encoder(new GsonEncoder())
        .decoder(new GsonDecoder())
        .target(OrderClient.class, "http://localhost:8080/order");
    ApplicationContext applicationContext = SpringApplication.run(ShoppingCartApplication.class, args);
  }
}
