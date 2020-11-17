package com.devonfw.app.java.order.orderservice.dataaccess.api;

import javax.persistence.Entity;

import com.devonfw.app.java.order.general.dataaccess.api.ApplicationPersistenceEntity;
import com.devonfw.app.java.order.orderservice.common.api.Item;

@Entity(name = "Item")
public class ItemEntity extends ApplicationPersistenceEntity implements Item {

  private String name;

  private String description;

  private Double price;

  private static final long serialVersionUID = 1L;

  public String getName() {

    return this.name;
  }

  public void setName(String name) {

    this.name = name;
  }

  public String getDescription() {

    return this.description;
  }

  public void setDescription(String description) {

    this.description = description;
  }

  public Double getPrice() {

    return this.price;
  }

  public void setPrice(Double price) {

    this.price = price;
  }

}
