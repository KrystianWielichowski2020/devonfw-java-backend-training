package com.devonfw.app.java.order.orderservice.logic.api.to;

import org.springframework.data.domain.Pageable;

import com.devonfw.module.basic.common.api.query.StringSearchConfigTo;
import com.devonfw.module.basic.common.api.to.AbstractTo;

public class ItemSearchCriteriaTo extends AbstractTo {

  private String name;

  private String description;

  private Double price;

  private StringSearchConfigTo nameOption;

  private StringSearchConfigTo descriptionOption;

  private Pageable pageable;

  public String getName() {

    return this.name;
  }

  public void setName(String name) {

    this.name = name;
  }

  public String getDescription() {

    return this.description;
  }

  public void setDescription(String description) {

    this.description = description;
  }

  public Double getPrice() {

    return this.price;
  }

  public void setPrice(Double price) {

    this.price = price;
  }

  public StringSearchConfigTo getNameOption() {

    return this.nameOption;
  }

  public void setNameOption(StringSearchConfigTo nameOption) {

    this.nameOption = nameOption;
  }

  public StringSearchConfigTo getDescriptionOption() {

    return this.descriptionOption;
  }

  public void setDescriptionOption(StringSearchConfigTo descriptionOption) {

    this.descriptionOption = descriptionOption;
  }

  public Pageable getPageable() {

    return this.pageable;
  }

  public void setPageable(Pageable pageable) {

    this.pageable = pageable;
  }
}
