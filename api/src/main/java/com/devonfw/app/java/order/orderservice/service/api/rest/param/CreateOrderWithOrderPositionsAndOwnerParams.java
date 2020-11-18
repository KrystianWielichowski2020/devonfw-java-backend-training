package com.devonfw.app.java.order.orderservice.service.api.rest.param;

import java.util.Set;

/**
 * @author KWIELICH
 *
 */
public class CreateOrderWithOrderPositionsAndOwnerParams {
  private Set<Long> orderPositionsIds; 
  private Long ownerId;
  
  public Set<Long> getOrderPositionsIds() {
  
    return this.orderPositionsIds;
  }
  public void setOrderPositionsIds(Set<Long> orderPositionsIds) {
  
    this.orderPositionsIds = orderPositionsIds;
  }
  public Long getOwnerId() {
  
    return this.ownerId;
  }
  public void setOwnerId(Long ownerId) {
  
    this.ownerId = ownerId;
  }
  
  
}
