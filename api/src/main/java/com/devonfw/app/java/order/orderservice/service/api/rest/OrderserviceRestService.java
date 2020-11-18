package com.devonfw.app.java.order.orderservice.service.api.rest;

import java.util.Collection;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;

import com.devonfw.app.java.order.orderservice.logic.api.Orderservice;
import com.devonfw.app.java.order.orderservice.logic.api.to.CustomerEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.CustomerSearchCriteriaTo;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemSearchCriteriaTo;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderCto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderSearchCriteriaTo;
import com.devonfw.app.java.order.orderservice.service.api.rest.param.CreateOrderWithOrderPositionsAndOwnerParams;
import com.devonfw.app.java.order.orderservice.service.api.rest.param.FindOrdersFromDateWithStatusParams;
import com.devonfw.app.java.order.orderservice.service.api.rest.param.NameAndPrice;
import com.devonfw.module.rest.common.api.RestService;

@Path("/orderservice/v1")
@Consumes(MediaType.APPLICATION_JSON_VALUE)
@Produces(MediaType.APPLICATION_JSON_VALUE)
public interface OrderserviceRestService extends RestService {

  @GET
  @Path("/item/{name}/")
  public Set<ItemEto> findItemsByName(@PathParam("name") String name);

  @POST
  @Path("/order/find/")
  public Collection<OrderEto> findOrdersFromDateWithStatus(FindOrdersFromDateWithStatusParams params);

  @POST
  @Path("/item/")
  public ItemEto updatePriceByItemsName(NameAndPrice params);

  @POST
  @Path("/order/")
  public OrderCto createOrderWithOrderPositionsAndOwner(CreateOrderWithOrderPositionsAndOwnerParams params);

  /**
   * Delegates to {@link Orderservice#findCustomer}.
   *
   * @param id the ID of the {@link CustomerEto}
   * @return the {@link CustomerEto}
   */
  @GET
  @Path("/customer/{id}/")
  public CustomerEto getCustomer(@PathParam("id") long id);

  /**
   * Delegates to {@link Orderservice#saveCustomer}.
   *
   * @param customer the {@link CustomerEto} to be saved
   * @return the recently created {@link CustomerEto}
   */
  @POST
  @Path("/customer/")
  public CustomerEto saveCustomer(CustomerEto customer);

  /**
   * Delegates to {@link Orderservice#deleteCustomer}.
   *
   * @param id ID of the {@link CustomerEto} to be deleted
   */
  @DELETE
  @Path("/customer/{id}/")
  public void deleteCustomer(@PathParam("id") long id);

  /**
   * Delegates to {@link Orderservice#findCustomerEtos}.
   *
   * @param searchCriteriaTo the pagination and search criteria to be used for finding customers.
   * @return the {@link Page list} of matching {@link CustomerEto}s.
   */
  @Path("/customer/search")
  @POST
  public Set<CustomerEto> findCustomers(CustomerSearchCriteriaTo searchCriteriaTo);

  /**
   * Delegates to {@link Orderservice#findOrder}.
   *
   * @param id the ID of the {@link OrderEto}
   * @return the {@link OrderEto}
   */
  @GET
  @Path("/order/{id}/")
  public OrderEto getOrder(@PathParam("id") long id);

  /**
   * Delegates to {@link Orderservice#saveOrder}.
   *
   * @param order the {@link OrderEto} to be saved
   * @return the recently created {@link OrderEto}
   */
  @POST
  @Path("/order/")
  public OrderEto saveOrder(OrderEto order);

  /**
   * Delegates to {@link Orderservice#deleteOrder}.
   *
   * @param id ID of the {@link OrderEto} to be deleted
   */
  @DELETE
  @Path("/order/{id}/")
  public void deleteOrder(@PathParam("id") long id);

  /**
   * Delegates to {@link Orderservice#findOrderEtos}.
   *
   * @param searchCriteriaTo the pagination and search criteria to be used for finding orders.
   * @return the {@link Page list} of matching {@link OrderEto}s.
   */

  @POST
  @Path("/order/search")
  public Set<OrderEto> findOrders(OrderSearchCriteriaTo searchCriteriaTo);

  /**
   * Delegates to {@link Orderservice#findItem}.
   *
   * @param id the ID of the {@link ItemEto}
   * @return the {@link ItemEto}
   */
  @GET
  @Path("/item/{id}/")
  public ItemEto getItem(@PathParam("id") long id);

  /**
   * Delegates to {@link Orderservice#saveItem}.
   *
   * @param item the {@link ItemEto} to be saved
   * @return the recently created {@link ItemEto}
   */
  @POST
  @Path("/item/")
  public ItemEto saveItem(ItemEto item);

  /**
   * Delegates to {@link Orderservice#deleteItem}.
   *
   * @param id ID of the {@link ItemEto} to be deleted
   */
  @DELETE
  @Path("/item/{id}/")
  public void deleteItem(@PathParam("id") long id);

  /**
   * Delegates to {@link Orderservice#findItemEtos}.
   *
   * @param searchCriteriaTo the pagination and search criteria to be used for finding items.
   * @return the {@link Page list} of matching {@link ItemEto}s.
   */
  @POST
  @Path("/item/search")
  public Set<ItemEto> findItems(ItemSearchCriteriaTo searchCriteriaTo);

}
