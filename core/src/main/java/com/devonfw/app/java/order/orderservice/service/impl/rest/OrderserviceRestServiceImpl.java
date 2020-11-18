package com.devonfw.app.java.order.orderservice.service.impl.rest;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.PathParam;

import com.devonfw.app.java.order.orderservice.logic.api.Orderservice;
import com.devonfw.app.java.order.orderservice.logic.api.to.CustomerEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.CustomerSearchCriteriaTo;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemSearchCriteriaTo;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderCto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderSearchCriteriaTo;
import com.devonfw.app.java.order.orderservice.service.api.rest.OrderserviceRestService;
import com.devonfw.app.java.order.orderservice.service.api.rest.param.CreateOrderWithOrderPositionsAndOwnerParams;
import com.devonfw.app.java.order.orderservice.service.api.rest.param.FindOrdersFromDateWithStatusParams;
import com.devonfw.app.java.order.orderservice.service.api.rest.param.NameAndPrice;

/**
 * The service implementation for REST calls in order to execute the logic of component {@link Orderservice}.
 */
@Named("OrderserviceRestService")
public class OrderserviceRestServiceImpl implements OrderserviceRestService {

  @Inject
  private Orderservice orderservice;

  @Override
  public Set<ItemEto> findItemsByName(@PathParam("name") String name) {

    return (Set<ItemEto>) this.orderservice.findItemsByName(name).getContent();
  }

  @Override
  public Collection<OrderEto> findOrdersFromDateWithStatus(FindOrdersFromDateWithStatusParams params) {

    return this.orderservice.findOrdersFromDateWithStatus(params.getFromDate(), params.getStatus());
  }

  @Override
  public ItemEto updatePriceByItemsName(NameAndPrice params) {

    return this.orderservice.updatePriceByItemsName(params.getName(), params.getPrice());
  }

  @Override
  public OrderCto createOrderWithOrderPositionsAndOwner(CreateOrderWithOrderPositionsAndOwnerParams params) {

    Set<Long> orderPositionsIds = params.getOrderPositionsIds();
    Long ownerId = params.getOwnerId();

    Set<ItemEto> orderPositions = new HashSet();
    for (Long id : orderPositionsIds) {
      orderPositions.add(this.orderservice.findItem(id));
    }
    CustomerEto owner = this.orderservice.findCustomer(ownerId);

    return this.orderservice.createOrderWithOrderPositionsAndOwner(orderPositions, owner);

  }

  @Override
  public CustomerEto getCustomer(long id) {

    return this.orderservice.findCustomer(id);
  }

  @Override
  public CustomerEto saveCustomer(CustomerEto customer) {

    return this.orderservice.saveCustomer(customer);
  }

  @Override
  public void deleteCustomer(long id) {

    this.orderservice.deleteCustomer(id);
  }

  @Override
  public Set<CustomerEto> findCustomers(CustomerSearchCriteriaTo searchCriteriaTo) {

    return (Set<CustomerEto>) this.orderservice.findCustomers(searchCriteriaTo).getContent();
  }

  @Override
  public OrderEto getOrder(long id) {

    return this.orderservice.findOrder(id);
  }

  @Override
  public OrderEto saveOrder(OrderEto order) {

    return this.orderservice.saveOrder(order);
  }

  @Override
  public void deleteOrder(long id) {

    this.orderservice.deleteOrder(id);
  }

  @Override
  public Set<OrderEto> findOrders(OrderSearchCriteriaTo searchCriteriaTo) {

    return (Set<OrderEto>) this.orderservice.findOrders(searchCriteriaTo).getContent();
  }

  @Override
  public ItemEto getItem(long id) {

    return this.orderservice.findItem(id);
  }

  @Override
  public ItemEto saveItem(ItemEto item) {

    return this.orderservice.saveItem(item);
  }

  @Override
  public void deleteItem(long id) {

    this.orderservice.deleteItem(id);
  }

  @Override
  public Set<ItemEto> findItems(ItemSearchCriteriaTo searchCriteriaTo) {

    return (Set<ItemEto>) this.orderservice.findItems(searchCriteriaTo).getContent();
  }

}
