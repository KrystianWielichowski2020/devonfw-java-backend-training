
package com.devonfw.app.java.order.orderservice.dataaccess.api.repo;

import static com.querydsl.core.alias.Alias.$;

import java.sql.Date;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devonfw.app.java.order.orderservice.common.api.OrderStatus;
import com.devonfw.app.java.order.orderservice.dataaccess.api.CustomerEntity;
import com.devonfw.app.java.order.orderservice.dataaccess.api.ItemEntity;
import com.devonfw.app.java.order.orderservice.dataaccess.api.OrderEntity;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderSearchCriteriaTo;
import com.devonfw.module.jpa.dataaccess.api.QueryUtil;
import com.devonfw.module.jpa.dataaccess.api.data.DefaultRepository;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * {@link DefaultRepository} for {@link OrderEntity}
 */
public interface OrderRepository extends DefaultRepository<OrderEntity> {

  /**
   * @param criteria the {@link OrderSearchCriteriaTo} with the criteria to search.
   * @return the {@link Page} of the {@link OrderEntity} objects that matched the search. If no pageable is set, it will
   *         return a unique page with all the objects that matched the search.
   */
  default Page<OrderEntity> findByCriteria(OrderSearchCriteriaTo criteria) {

    OrderEntity alias = newDslAlias();
    JPAQuery<OrderEntity> query = newDslQuery(alias);

    Date creationDate = criteria.getCreationDate();
    if (creationDate != null) {
      query.where($(alias.getCreationDate()).eq(creationDate));
    }
    Long owner = criteria.getOwnerId();
    if (owner != null) {
      query.where($(alias.getOwner().getId()).eq(owner));
    }
    Double price = criteria.getPrice();
    if (price != null) {
      query.where($(alias.getPrice()).eq(price));
    }
    OrderStatus status = criteria.getStatus();
    if (status != null) {
      query.where($(alias.getStatus()).eq(status));
    }
    if (criteria.getPageable() == null) {
      criteria.setPageable(PageRequest.of(0, Integer.MAX_VALUE));
    } else {
      addOrderBy(query, alias, criteria.getPageable().getSort());
    }

    return QueryUtil.get().findPaginated(criteria.getPageable(), query, true);
  }

  @Query("SELECT o FROM OrderSummary o WHERE o.creationDate >= :fromDate AND o.status = :status")
  public Collection<OrderEntity> findOrdersFromDateWithStatus(@Param("fromDate") Date fromDate,
      @Param("status") OrderStatus status);

  public default OrderEntity createOrderWithOrderPositionsAndOwner(Set<ItemEntity> orderPositions,
      CustomerEntity owner) {

    OrderEntity order = new OrderEntity();
    order.setModificationCounter(0);
    order.setOwner(owner);
    order.setOrderPositions(orderPositions);
    order.setCreationDate(new Date(System.currentTimeMillis()));
    order.setStatus(OrderStatus.NEW);

    return save(order);
  }

  /**
   * Add sorting to the given query on the given alias
   *
   * @param query to add sorting to
   * @param alias to retrieve columns from for sorting
   * @param sort specification of sorting
   */
  public default void addOrderBy(JPAQuery<OrderEntity> query, OrderEntity alias, Sort sort) {

    if (sort != null && sort.isSorted()) {
      Iterator<Order> it = sort.iterator();
      while (it.hasNext()) {
        Order next = it.next();
        switch (next.getProperty()) {
          case "creationDate":
            if (next.isAscending()) {
              query.orderBy($(alias.getCreationDate()).asc());
            } else {
              query.orderBy($(alias.getCreationDate()).desc());
            }
            break;
          case "owner":
            if (next.isAscending()) {
              query.orderBy($(alias.getOwner().getId().toString()).asc());
            } else {
              query.orderBy($(alias.getOwner().getId().toString()).desc());
            }
            break;
          case "price":
            if (next.isAscending()) {
              query.orderBy($(alias.getPrice()).asc());
            } else {
              query.orderBy($(alias.getPrice()).desc());
            }
            break;
          case "status":
            if (next.isAscending()) {
              query.orderBy($(alias.getStatus()).asc());
            } else {
              query.orderBy($(alias.getStatus()).desc());
            }
            break;
          default:
            throw new IllegalArgumentException("Sorted by the unknown property '" + next.getProperty() + "'");
        }
      }
    }
  }

}