package com.devonfw.app.java.order.orderservice.dataaccess.api.repo;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;

import com.devonfw.app.java.order.orderservice.dataaccess.api.ItemEntity;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemSearchCriteriaTo;
import com.devonfw.module.basic.common.api.query.LikePatternSyntax;
import com.devonfw.module.basic.common.api.query.StringSearchConfigTo;
import com.devonfw.module.test.common.base.ComponentTest;

/**
 * @author KWIELICH
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ItemRepositoryTest extends ComponentTest {

  @Inject
  private ItemRepository itemRepository;

  @Test
  public void shouldFindAllItems() {

    // when
    List<ItemEntity> foundItems = this.itemRepository.findAll();

    // then
    assertThat(foundItems).hasSize(6);
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

    String[] expectedItemsNames = { "spaghetti ala biedra", "spaghetti bolognese", "spaghetti carbonara" };

    // when
    Page<ItemEntity> pages = this.itemRepository.findByCriteria(criteria);

    // then
    List<ItemEntity> foundItems = pages.getContent();
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
    ItemEntity result = this.itemRepository.updatePriceByItemsName(itemName, newPrice);

    // then
    assertNotNull(result);
    assertThat(result.getName()).isEqualTo(itemName);
    assertThat(result.getPrice()).isEqualTo(newPrice);
  }
}
