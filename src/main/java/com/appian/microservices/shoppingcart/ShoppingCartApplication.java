package com.appian.microservices.shoppingcart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Shopping cart application.
 *
 * @author touré
 */
@SpringBootApplication
@RestController
public class ShoppingCartApplication {

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
    getCartByUserId(userId).addItem(item);
    return ResponseEntity.ok().build();
  }

  @PostMapping(value = "/{userId}/remove")
  public ResponseEntity<Void> remove(@PathVariable String userId, @RequestBody Item input) {
    getCartByUserId(userId).removeItem(input.getUuid());
    return ResponseEntity.ok().build();
  }

  // Functions to add
  // Update Quantity
  //

  public Cart getCartByUserId(String userId) {
    return carts.stream().filter(cart -> cart.getUserId().equals(userId)).findFirst().get();
  }

  public static void main(String[] args) {
    ApplicationContext applicationContext = SpringApplication.run(ShoppingCartApplication.class, args);
  }
}
