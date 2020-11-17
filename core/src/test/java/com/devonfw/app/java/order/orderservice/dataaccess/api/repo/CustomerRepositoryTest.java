package com.devonfw.app.java.order.orderservice.dataaccess.api.repo;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.devonfw.app.java.order.orderservice.dataaccess.api.CustomerEntity;
import com.devonfw.module.test.common.base.ComponentTest;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class CustomerRepositoryTest extends ComponentTest {

  @Inject
  private CustomerRepository customerRepository;

  @Inject
  private OrderRepository orderRepository;

  @Before
  public void initData() {

    this.orderRepository.deleteAll();
    this.customerRepository.deleteAll();

    List<CustomerEntity> customersToSave = createData();

    this.customerRepository.saveAll(customersToSave);
  }

  @Override
  protected void doTearDown() {

    super.doTearDown();
    this.customerRepository.deleteAll();
  }

  @Test
  public void shouldRemoveCustomer() {

    // Given
    Long deletedCustomerId = 1000002L;

    // When
    List<CustomerEntity> allCustomers1 = this.customerRepository.findAll();
    this.customerRepository.deleteById(deletedCustomerId);

    // Then
    List<CustomerEntity> allCustomers = this.customerRepository.findAll();
    assertThat(allCustomers.size()).isEqualTo(2);

    Optional<CustomerEntity> deletedCustomer = this.customerRepository.findById(deletedCustomerId);
    assertFalse(deletedCustomer.isPresent());
  }

  private List<CustomerEntity> createData() {

    List<CustomerEntity> customers = new ArrayList<>();

    customers.add(createCustomer(1000000L, "Pawel", "Kowal"));
    customers.add(createCustomer(1000001L, "Adam", "Malysz"));
    customers.add(createCustomer(1000002L, "Jan", "Nowak"));

    return customers;
  }

  private CustomerEntity createCustomer(Long id, String firstname, String lastname) {

    CustomerEntity customer = new CustomerEntity();
    customer.setId(id);
    customer.setModificationCounter(0);
    customer.setFirstname(firstname);
    customer.setLastname(lastname);
    return customer;
  }
}
