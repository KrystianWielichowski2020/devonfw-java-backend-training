package com.devonfw.app.java.order.orderservice.service.api.rest.param;

import java.sql.Date;

import com.devonfw.app.java.order.orderservice.common.api.OrderStatus;

public class FindOrdersFromDateWithStatusParams {

  private Date fromDate;

  private OrderStatus status;

  public Date getFromDate() {

    return this.fromDate;
  }

  public void setFromDate(Date fromDate) {

    this.fromDate = fromDate;
  }

  public OrderStatus getStatus() {

    return this.status;
  }

  public void setStatus(OrderStatus status) {

    this.status = status;
  }

}
