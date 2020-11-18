package com.devonfw.app.java.order.orderservice.logic.impl;

import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import com.devonfw.app.java.order.SpringBootApp;
import com.devonfw.app.java.order.orderservice.logic.api.Orderservice;
import com.devonfw.app.java.order.orderservice.logic.api.to.CustomerEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemSearchCriteriaTo;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderCto;
import com.devonfw.module.basic.common.api.query.LikePatternSyntax;
import com.devonfw.module.basic.common.api.query.StringSearchConfigTo;
import com.devonfw.module.test.common.base.ComponentTest;

@Transactional
@SpringBootTest(classes = { SpringBootApp.class }, webEnvironment = WebEnvironment.NONE)
public class OrderServiceImplTest extends ComponentTest {

  @Inject
  private Orderservice orderService;

  @Test
  public void shouldFindItem() {

    // given
    Long givenId = 23L;
    ItemEto expectedItem = createItem(givenId, "pizza", "Italy", 220D);

    // when
    ItemEto result = this.orderService.findItem(givenId);

    // then
    assertNotNull(result);
    assertThat(result).isEqualTo(expectedItem);
  }

  @Test
  public void shouldFindItemsByCriteria() {

    // given
    ItemSearchCriteriaTo criteria = new ItemSearchCriteriaTo();
    criteria.setName("Spaghetti*");
    StringSearchConfigTo nameOption = new StringSearchConfigTo();
    nameOption.setIgnoreCase(true);
    nameOption.setLikeSyntax(LikePatternSyntax.GLOB);
    criteria.setNameOption(nameOption);

    String[] expectedItemsNames = { "spaghetti ala biedra", "spaghetti bolognese", "spaghetti carbonara" };

    // when
    Page<ItemEto> pages = this.orderService.findItems(criteria);

    // then
    List<ItemEto> foundItems = pages.getContent();
    assertThat(foundItems).hasSize(expectedItemsNames.length);

    for (int i = 0; i < foundItems.size(); i++) {
      assertThat(foundItems.get(i).getName()).isEqualTo(expectedItemsNames[i]);
    }

  }

  @Test
  public void shouldChangeItemsPrice() {

    // given
    String itemName = "pizza";
    Double newPrice = 500D;

    // when
    ItemEto result = this.orderService.updatePriceByItemsName(itemName, newPrice);

    // then
    assertNotNull(result);
    assertThat(result.getName()).isEqualTo(itemName);
    assertThat(result.getPrice()).isEqualTo(newPrice);
  }

  @Test
  public void shouldCreateOrder() {

    // Given
    CustomerEto expectedCustomer = this.orderService.findCustomer(31L);

    ItemEto expectedItem1 = this.orderService.findItem(25L);
    ItemEto expectedItem2 = this.orderService.findItem(26L);

    Set<ItemEto> items = new HashSet<>();
    items.add(expectedItem1);
    items.add(expectedItem2);

    // When
    OrderCto result = this.orderService.createOrderWithOrderPositionsAndOwner(items, expectedCustomer);

    // Then
    assertNotNull(result);
    assertThat(result.getOwner().getId()).isEqualTo(expectedCustomer.getId());

    Set<ItemEto> itemsFromResult = result.getOrderPositions();
    assertThat(itemsFromResult.size()).isEqualTo(2);
    assertThat(itemsFromResult).contains(expectedItem1, expectedItem2);
  }

  @Test
  public void shouldFindByName() {

    // given
    String name = "Spaghetti*";

    String[] expectedItemsNames = { "spaghetti ala biedra", "spaghetti bolognese", "spaghetti carbonara" };

    // when
    Page<ItemEto> pages = this.orderService.findItemsByName(name);

    // then
    List<ItemEto> foundItems = pages.getContent();
    assertThat(foundItems).hasSize(expectedItemsNames.length);

    for (int i = 0; i < foundItems.size(); i++) {
      assertThat(foundItems.get(i).getName()).isEqualTo(expectedItemsNames[i]);
    }
  }

  private ItemEto createItem(Long id, String name, String description, Double price) {

    ItemEto item = new ItemEto();
    item.setId(id);
    item.setModificationCounter(0);
    item.setName(name);
    item.setDescription(description);
    item.setPrice(price);

    return item;
  }

}
