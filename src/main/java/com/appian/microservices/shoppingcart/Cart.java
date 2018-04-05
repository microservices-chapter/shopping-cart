package com.appian.microservices.shoppingcart;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cart {
  private static final Logger LOG = LoggerFactory.getLogger(Cart.class);

  private String userId;
  private List<Item> items;

  public Cart(String userId) {
    this.userId = userId;
    this.items = new ArrayList<Item>();
  }

  public Cart(String userId, List items) {
    this.userId = userId;
    this.items = items;
  }

  public List getItems() {
    return this.items;
  }

  public void addItem(Item item) {
    LOG.info("Adding " + item + " to " + userId + "'s cart.");
    this.items.add(item);
  }

  public void removeItem(String uuid) {
    Item itemToRemove = this.items.stream().filter(item -> item.getUuid().equals(uuid)).findFirst().get();
    LOG.info("Removing " + itemToRemove + " from " + userId + "'s cart.");
    this.items.remove(itemToRemove);
  }

  public String getUserId() { return this.userId; }

}
