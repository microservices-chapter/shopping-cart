package com.appian.microservices.shoppingcart;

public class Item {
  private String uuid;
  private int quantity;

  public Item () {
    this.uuid = "";
    this.quantity = 0;
  }

  public Item(String uuid, int quantity) {
    this.uuid = uuid;
    this.quantity = quantity;
  }

  public String getUuid() {
    return this.uuid;
  }

  public int getQuantity() {
    return this.quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String toString() {
    // In the future this might include information from the catalog
    return "{ uuid: " + this.uuid + ", quantity: " + this.quantity + "}";
  }
}
