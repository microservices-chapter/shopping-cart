package com.appian.microservices.shoppingcart;

import java.util.ArrayList;
import java.util.List;

public class Cart {
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
    this.items.add(item);
  }

  public void removeItem(String uuid) {
    Item itemToRemove = this.items.stream().filter(item -> item.getUuid().equals(uuid)).findFirst().get();
    this.items.remove(itemToRemove);
  }

  public String getUserId() { return this.userId; }

}
