package com.devonfw.app.java.order.orderservice.dataaccess.api.repo;

import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.devonfw.app.java.order.orderservice.common.api.OrderStatus;
import com.devonfw.app.java.order.orderservice.dataaccess.api.CustomerEntity;
import com.devonfw.app.java.order.orderservice.dataaccess.api.ItemEntity;
import com.devonfw.app.java.order.orderservice.dataaccess.api.OrderEntity;
import com.devonfw.module.test.common.base.ComponentTest;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class OrderRepositoryTest extends ComponentTest {

  @Inject
  private CustomerRepository customerRepository;

  @Inject
  private ItemRepository itemRepository;

  @Inject
  private OrderRepository orderRepository;

  @Before
  public void initData() {

    this.orderRepository.deleteAll();

    CustomerEntity customerToSave = new CustomerEntity();
    customerToSave.setModificationCounter(0);
    customerToSave.setFirstname("Jan");
    customerToSave.setLastname("Nowak");

    Long customerId = this.customerRepository.save(customerToSave).getId();

    List<OrderEntity> ordersToSave = createData(customerId);

    this.orderRepository.saveAll(ordersToSave);
  }

  @Override
  protected void doTearDown() {

    super.doTearDown();
    this.orderRepository.deleteAll();
    this.itemRepository.deleteAll();
    this.customerRepository.deleteAll();
  }

  @Test
  public void shouldFindOrdersAfterGivenDateAndWithStatusNew() {

    // Given
    Date givenDate = Date.valueOf("2020-03-10");
    OrderStatus givenStatus = OrderStatus.NEW;

    // When
    List<OrderEntity> results = (List<OrderEntity>) this.orderRepository.findOrdersFromDateWithStatus(givenDate,
        givenStatus);

    // Then
    assertThat(results.size()).isEqualTo(2);

    for (OrderEntity result : results) {
      assertThat(result.getCreationDate()).isAfter(givenDate);
      assertThat(result.getStatus()).isEqualTo(givenStatus);
    }
  }

  @Test
  public void shouldCreateOrder() {

    // Given
    CustomerEntity customerToSave = new CustomerEntity();
    customerToSave.setModificationCounter(0);
    customerToSave.setFirstname("Bogumil");
    customerToSave.setLastname("Swiety");

    CustomerEntity expectedCustomer = this.customerRepository.save(customerToSave);

    ItemEntity itemToSave1 = new ItemEntity();
    itemToSave1.setModificationCounter(0);
    itemToSave1.setName("pierogi");
    itemToSave1.setPrice(15D);

    ItemEntity itemToSave2 = new ItemEntity();
    itemToSave2.setModificationCounter(0);
    itemToSave2.setName("bigos");
    itemToSave2.setPrice(18D);

    ItemEntity expectedItem1 = this.itemRepository.save(itemToSave1);
    ItemEntity expectedItem2 = this.itemRepository.save(itemToSave2);

    Set<ItemEntity> items = new HashSet<>();
    items.add(expectedItem1);
    items.add(expectedItem2);

    // When
    OrderEntity result = this.orderRepository.createOrderWithOrderPositionsAndOwner(items, expectedCustomer);

    // Then
    assertNotNull(result);
    Set<ItemEntity> itemsFromResult = result.getOrderPositions();
    assertThat(itemsFromResult.size()).isEqualTo(2);
    assertThat(itemsFromResult).contains(expectedItem1, expectedItem2);

  }

  private List<OrderEntity> createData(Long customerId) {

    List<OrderEntity> orders = new ArrayList<>();

    OrderEntity order1 = createOrder(1L, Date.valueOf("2020-03-12"), customerId, OrderStatus.PAID);
    orders.add(order1);

    OrderEntity order2 = createOrder(2L, Date.valueOf("2020-02-12"), customerId, OrderStatus.NEW);
    orders.add(order2);

    OrderEntity order3 = createOrder(3L, Date.valueOf("2020-04-13"), customerId, OrderStatus.NEW);
    orders.add(order3);

    OrderEntity order4 = createOrder(4L, Date.valueOf("2020-10-01"), customerId, OrderStatus.NEW);
    orders.add(order4);

    return orders;
  }

  private OrderEntity createOrder(Long id, Date creationDate, Long customerId, OrderStatus status) {

    OrderEntity order = new OrderEntity();
    order.setId(id);
    order.setModificationCounter(0);
    order.setCreationDate(creationDate);
    order.setOwnerId(customerId);
    order.setStatus(status);

    return order;
  }
}
