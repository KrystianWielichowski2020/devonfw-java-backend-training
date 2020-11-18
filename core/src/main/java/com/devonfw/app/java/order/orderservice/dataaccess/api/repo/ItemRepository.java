package com.devonfw.app.java.order.orderservice.dataaccess.api.repo;

import static com.querydsl.core.alias.Alias.$;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.devonfw.app.java.order.orderservice.dataaccess.api.ItemEntity;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemSearchCriteriaTo;
import com.devonfw.module.basic.common.api.query.LikePatternSyntax;
import com.devonfw.module.basic.common.api.query.StringSearchConfigTo;
import com.devonfw.module.jpa.dataaccess.api.QueryUtil;
import com.devonfw.module.jpa.dataaccess.api.data.DefaultRepository;
import com.querydsl.jpa.impl.JPAQuery;

public interface ItemRepository extends DefaultRepository<ItemEntity> {

  public default ItemEntity updatePriceByItemsName(String name, Double price) {

    ItemEntity updatedItem = null;
    List<ItemEntity> itemsToUpdate = findItemsByName(name).getContent();

    if (CollectionUtils.isNotEmpty(itemsToUpdate)) {
      ItemEntity itemToUpdate = itemsToUpdate.get(0);
      itemToUpdate.setPrice(price);
      updatedItem = save(itemToUpdate);
    }

    return updatedItem;
  }

  public default Page<ItemEntity> findItemsByName(String name) {

    ItemSearchCriteriaTo criteria = new ItemSearchCriteriaTo();
    criteria.setName(name);
    StringSearchConfigTo nameOption = new StringSearchConfigTo();
    nameOption.setIgnoreCase(true);
    nameOption.setLikeSyntax(LikePatternSyntax.GLOB);
    criteria.setNameOption(nameOption);

    return findByCriteria(criteria);
  }

  default Page<ItemEntity> findByCriteria(ItemSearchCriteriaTo criteria) {

    ItemEntity alias = newDslAlias();
    JPAQuery<ItemEntity> query = newDslQuery(alias);

    String name = criteria.getName();
    if (name != null && !name.isEmpty()) {
      QueryUtil.get().whereString(query, $(alias.getName()), name, criteria.getNameOption());
    }

    String description = criteria.getDescription();
    if (StringUtils.isNotEmpty(description)) {
      QueryUtil.get().whereString(query, $(alias.getDescription()), description, criteria.getDescriptionOption());
    }

    Double price = criteria.getPrice();
    if (price != null) {
      query.where($(alias.getPrice()).eq(price));
    }
    Sort sort = Sort.by("name");
    Pageable pageable = PageRequest.of(0, 20, sort);

    if (criteria.getPageable() == null) {
      criteria.setPageable(PageRequest.of(0, Integer.MAX_VALUE));
    } else {
      addOrderBy(query, alias, criteria.getPageable().getSort());
    }

    return QueryUtil.get().findPaginated(pageable, query, true);

  }

  public default void addOrderBy(JPAQuery<ItemEntity> query, ItemEntity alias, Sort sort) {

    if (sort != null && sort.isSorted()) {
      Iterator<Order> it = sort.iterator();
      while (it.hasNext()) {
        Order next = it.next();
        switch (next.getProperty()) {
          case "name":
            if (next.isAscending()) {
              query.orderBy($(alias.getName()).asc());
            } else {
              query.orderBy($(alias.getName()).desc());
            }
            break;
          case "description":
            if (next.isAscending()) {
              query.orderBy($(alias.getDescription().toString()).asc());
            } else {
              query.orderBy($(alias.getDescription().toString()).desc());
            }
            break;
          case "price":
            if (next.isAscending()) {
              query.orderBy($(alias.getPrice()).asc());
            } else {
              query.orderBy($(alias.getPrice()).desc());
            }
            break;
          default:
            throw new IllegalArgumentException("Sorted by the unknown property '" + next.getProperty() + "'");
        }
      }
    }
  }

}
