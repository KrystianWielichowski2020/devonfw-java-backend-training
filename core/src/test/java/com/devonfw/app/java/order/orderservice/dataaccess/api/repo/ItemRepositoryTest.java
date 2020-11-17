package com.devonfw.app.java.order.orderservice.dataaccess.api.repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;

import com.devonfw.app.java.order.orderservice.dataaccess.api.ItemEntity;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemSearchCriteriaTo;
import com.devonfw.module.basic.common.api.query.LikePatternSyntax;
import com.devonfw.module.basic.common.api.query.StringSearchConfigTo;
import com.devonfw.module.test.common.base.ComponentTest;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ItemRepositoryTest extends ComponentTest {

  @Inject
  private ItemRepository itemRepository;

  @Inject
  private OrderRepository orderRepository;

  @Before
  public void initData() {

    this.orderRepository.deleteAll();
    this.itemRepository.deleteAll();

    List<ItemEntity> itemsToSave = createData();

    this.itemRepository.saveAll(itemsToSave);
  }

  @Override
  protected void doTearDown() {

    super.doTearDown();
    this.itemRepository.deleteAll();
  }

  @Test
  public void shouldFindAllItems() {

    // when
    List<ItemEntity> foundItems = this.itemRepository.findAll();

    // then
    assertThat(foundItems).hasSize(3);
  }

  @Test
  public void shouldFindByName() {

    // given
    ItemSearchCriteriaTo criteria = new ItemSearchCriteriaTo();
    criteria.setName("Spaghetti*");
    StringSearchConfigTo nameOption = new StringSearchConfigTo();
    nameOption.setIgnoreCase(true);
    nameOption.setLikeSyntax(LikePatternSyntax.GLOB);
    criteria.setNameOption(nameOption);

    // when
    Page<ItemEntity> pages = this.itemRepository.findByCriteria(criteria);

    // then
    List<ItemEntity> foundItems = pages.getContent();
    assertThat(foundItems).hasSize(2);
    assertEquals("spaghetti bolognese", foundItems.get(0).getName());
    assertEquals("spaghetti carbonara", foundItems.get(1).getName());
  }

  @Test
  public void shouldChangeItemsPrice() {

    // given
    String itemName = "pizza";
    Double newPrice = 10D;

    // when
    ItemEntity result = this.itemRepository.updatePriceByItemsName(itemName, newPrice);

    // then
    assertNotNull(result);
    assertThat(result.getName()).isEqualTo(itemName);
    assertThat(result.getPrice()).isEqualTo(newPrice);
  }

  private List<ItemEntity> createData() {

    List<ItemEntity> items = new ArrayList<>();
    ItemEntity item1 = createItem(1L, "spaghetti carbonara", "Italy", 20D);
    ItemEntity item2 = createItem(2L, "spaghetti bolognese", "Italy", 22D);
    ItemEntity item3 = createItem(2L, "pizza", "Italy", 18D);

    items.add(item1);
    items.add(item2);
    items.add(item3);

    return items;
  }

  private ItemEntity createItem(Long id, String name, String desc, Double price) {

    ItemEntity item = new ItemEntity();
    item.setId(id);
    item.setModificationCounter(0);
    item.setName(name);
    item.setDescription(desc);
    item.setPrice(price);
    return item;
  }
}
