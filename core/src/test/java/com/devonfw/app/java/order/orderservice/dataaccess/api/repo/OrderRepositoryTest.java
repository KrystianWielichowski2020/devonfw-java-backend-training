package com.devonfw.app.java.order.orderservice.dataaccess.api.repo;

import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.devonfw.app.java.order.orderservice.common.api.OrderStatus;
import com.devonfw.app.java.order.orderservice.dataaccess.api.CustomerEntity;
import com.devonfw.app.java.order.orderservice.dataaccess.api.ItemEntity;
import com.devonfw.app.java.order.orderservice.dataaccess.api.OrderEntity;
import com.devonfw.module.test.common.base.ComponentTest;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureBefore
public class OrderRepositoryTest extends ComponentTest {

  @Inject
  private CustomerRepository customerRepository;

  @Inject
  private ItemRepository itemRepository;

  @Inject
  private OrderRepository orderRepository;

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
    CustomerEntity expectedCustomer = this.customerRepository.find(31L);

    ItemEntity expectedItem1 = this.itemRepository.find(25L);
    ItemEntity expectedItem2 = this.itemRepository.find(26L);

    Set<ItemEntity> items = new HashSet<>();
    items.add(expectedItem1);
    items.add(expectedItem2);

    // When
    OrderEntity result = this.orderRepository.createOrderWithOrderPositionsAndOwner(items, expectedCustomer);

    // Then
    assertNotNull(result);
    assertThat(result.getOwner()).isEqualTo(expectedCustomer);

    Set<ItemEntity> itemsFromResult = result.getOrderPositions();
    assertThat(itemsFromResult.size()).isEqualTo(2);
    assertThat(itemsFromResult).contains(expectedItem1, expectedItem2);
  }
}
