package com.devonfw.app.java.order.orderservice.dataaccess.api.repo;

import static org.junit.Assert.assertFalse;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.devonfw.app.java.order.orderservice.dataaccess.api.CustomerEntity;
import com.devonfw.module.test.common.base.ComponentTest;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class CustomerRepositoryTest extends ComponentTest {

  @Inject
  private CustomerRepository customerRepository;

  @Test
  public void shouldRemoveCustomer() {

    // Given
    Long deletedCustomerId = 32L;

    // When
    this.customerRepository.deleteById(deletedCustomerId);

    // Then
    List<CustomerEntity> allCustomers = this.customerRepository.findAll();
    assertThat(allCustomers.size()).isEqualTo(4);

    Optional<CustomerEntity> deletedCustomer = this.customerRepository.findById(deletedCustomerId);
    assertFalse(deletedCustomer.isPresent());
  }
}
