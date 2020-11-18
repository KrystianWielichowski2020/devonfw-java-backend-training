package com.devonfw.app.java.order.orderservice.logic.impl.usecase;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.devonfw.app.java.order.orderservice.dataaccess.api.ItemEntity;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemEto;
import com.devonfw.app.java.order.orderservice.logic.api.usecase.UcManageItem;
import com.devonfw.app.java.order.orderservice.logic.base.usecase.AbstractItemUc;

@Named
@Validated
@Transactional
public class UcManageItemImpl extends AbstractItemUc implements UcManageItem {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManageItemImpl.class);

  @Override
  public boolean deleteItem(long itemId) {

    getItemRepository().deleteById(itemId);
    LOG.debug("The item with id '{}' has been deleted.", itemId);
    return true;
  }

  @Override
  public ItemEto saveItem(ItemEto item) {

    ItemEntity itemEntity = getBeanMapper().map(item, ItemEntity.class);
    ItemEntity itemResult = getItemRepository().save(itemEntity);
    LOG.debug("Customer with id '{}' has been created.", itemResult.getId());
    return getBeanMapper().map(itemResult, ItemEto.class);
  }

  @Override
  public ItemEto updatePriceByItemsName(String name, Double price) {

    ItemEntity itemResult = getItemRepository().updatePriceByItemsName(name, price);
    return getBeanMapper().map(itemResult, ItemEto.class);
  }
}
