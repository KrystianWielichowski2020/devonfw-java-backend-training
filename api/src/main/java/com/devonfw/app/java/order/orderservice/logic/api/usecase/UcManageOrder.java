package com.devonfw.app.java.order.orderservice.logic.api.usecase;

import java.util.Set;

import com.devonfw.app.java.order.orderservice.logic.api.to.CustomerEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderCto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderEto;

/**
 * Interface of UcManageOrder to centralize documentation and signatures of methods.
 */
public interface UcManageOrder {

  /**
   * Deletes a order from the database by its id 'orderId'.
   *
   * @param orderId Id of the order to delete
   * @return boolean <code>true</code> if the order can be deleted, <code>false</code> otherwise
   */
  boolean deleteOrder(long orderId);

  /**
   * Saves a order and store it in the database.
   *
   * @param order the {@link OrderEto} to create.
   * @return the new {@link OrderEto} that has been saved with ID and version.
   */
  OrderEto saveOrder(OrderEto order);

  OrderCto createOrderWithOrderPositionsAndOwner(Set<ItemEto> orderPositions, CustomerEto owner);

}
