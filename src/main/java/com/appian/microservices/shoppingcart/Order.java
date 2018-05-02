package com.appian.microservices.shoppingcart;

import java.util.Date;
import java.util.List;

public class Order {
  private String orderId;
  private String customerId;
  private Date dateCreated;
  private Date dateUpdated;

  public String getOrderId() {
    return orderId;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public Date getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }

  public Date getDateUpdated() {
    return dateUpdated;
  }

  public void setDateUpdated(Date dateUpdated) {
    this.dateUpdated = dateUpdated;
  }

  public List<OrderItem> getOrderItemList() {
    return orderItemList;
  }

  public void setOrderItemList(List<OrderItem> orderItemList) {
    this.orderItemList = orderItemList;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  private List<OrderItem> orderItemList;
}
