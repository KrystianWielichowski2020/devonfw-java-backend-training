package com.devonfw.app.java.order.orderservice.dataaccess.api;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.devonfw.app.java.order.general.dataaccess.api.ApplicationPersistenceEntity;
import com.devonfw.app.java.order.orderservice.common.api.Order;
import com.devonfw.app.java.order.orderservice.common.api.OrderStatus;

@Entity(name = "OrderSummary")
public class OrderEntity extends ApplicationPersistenceEntity implements Order {

  private Date creationDate;

  private Set<ItemEntity> orderPositions;

  private CustomerEntity owner;

  private Double price;

  private OrderStatus status;

  private static final long serialVersionUID = 1L;

  @Override
  public Date getCreationDate() {

    return this.creationDate;
  }

  @Override
  public void setCreationDate(Date creationDate) {

    this.creationDate = creationDate;
  }

  @ManyToMany
  @JoinTable(name = "OrderPosition", joinColumns = @JoinColumn(name = "orderId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "itemId", referencedColumnName = "id"))
  public Set<ItemEntity> getOrderPositions() {

    return this.orderPositions;
  }

  public void setOrderPositions(Set<ItemEntity> orderPositions) {

    this.orderPositions = orderPositions;
  }

  @ManyToOne
  @JoinColumn(name = "ownerId")
  public CustomerEntity getOwner() {

    return this.owner;
  }

  public void setOwner(CustomerEntity owner) {

    this.owner = owner;
  }

  @Override
  @Transient
  public Long getOwnerId() {

    if (getOwner() != null)
      return getOwner().getId();
    return null;
  }

  @Override
  public void setOwnerId(Long ownerId) {

    CustomerEntity e = new CustomerEntity();
    e.setId(ownerId);
    setOwner(e);
  }

  @Override
  public Double getPrice() {

    return this.price;
  }

  @Override
  public void setPrice(Double price) {

    this.price = price;
  }

  @Override
  @Enumerated(EnumType.STRING)
  public OrderStatus getStatus() {

    return this.status;
  }

  @Override
  public void setStatus(OrderStatus status) {

    this.status = status;
  }

}
