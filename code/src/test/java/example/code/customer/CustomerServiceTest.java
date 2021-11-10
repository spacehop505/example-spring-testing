package example.code.customer;

import example.code.exception.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

//@ExtendWith(MockitoExtension.class) same as AutoCloseable
public class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;
    @Mock
    private EmailValidator emailValidator;

    private AutoCloseable autoCloseable;
    private CustomerService service;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        service = new CustomerService(repository, emailValidator);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();

    }

    @Test
    void shouldUpdateCustomer1() {
        UUID id = UUID.randomUUID();
        Customer foundCustomer = new Customer(id, "Yellow", "bob1@gmail.com");
        Customer updatedCustomer = new Customer(id, "Maryam", "bob1@gmail.com");
        given(repository.findById(id)).willReturn(Optional.of(foundCustomer));
        //when(repository.findById(id)).thenReturn(Optional.of(foundCustomer));
        service.updateCustomer(updatedCustomer);
        ArgumentCaptor<Customer> studentArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(repository).save(studentArgumentCaptor.capture());
    }


    @Test
    void shouldUpdateCustomer2() {
        UUID id = UUID.randomUUID();
        Customer foundCustomer = new Customer(id, "Maryam", "bobp@gmail.com");
        Customer updatedCustomer = new Customer(id, "Maryam", "bob1@gmail.com");
        //given(repository.findById(id)).willReturn(Optional.of(customerCreate));
        when(repository.findById(id)).thenReturn(Optional.of(foundCustomer));
        service.updateCustomer(updatedCustomer);
        ArgumentCaptor<Customer> studentArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(repository).save(studentArgumentCaptor.capture());

    }


    @Test
    void shouldUpdateCustomer3() {
        UUID id = UUID.randomUUID();
        Customer foundCustomer = new Customer(id, "Maryam", "bob1@gmail.com");
        Customer updatedCustomer = new Customer(id, "Maryam", "bob1@gmail.com");
        //given(repository.findById(id)).willReturn(Optional.of(customerCreate));
        when(repository.findById(id)).thenReturn(Optional.of(foundCustomer));
        service.updateCustomer(updatedCustomer);
        ArgumentCaptor<Customer> studentArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(repository).save(studentArgumentCaptor.capture());
    }

    @Test
    void shouldNotUpdateCustomer() {
        UUID id = UUID.randomUUID();
        Customer updatedCustomer = new Customer(id, "Maryam", "bob1@gmail.com");
        //given(repository.findById(id)).willReturn(Optional.of(customerCreate));
        when(repository.findById(updatedCustomer.getId())).thenReturn(Optional.empty());
        // verify(repository)
        assertThatThrownBy(() -> service.updateCustomer(updatedCustomer)).isInstanceOf(BadRequestException.class).hasMessageContaining("not found");
        verify(repository, never()).save(any());
        verify(repository, never()).save(any(Customer.class));
    }

    @Test
    void shouldDeleteCustomer() {
        UUID id = UUID.randomUUID();
        given(repository.existsById(id)).willReturn(true);
        service.deleteCustomer(id);
        verify(repository).deleteById(id);
    }

    @Test
    void throwDeleteCustomer() {
        UUID id = UUID.randomUUID();
        given(repository.existsById(id)).willReturn(false);
        assertThatThrownBy(() -> service.deleteCustomer(id)).isInstanceOf(BadRequestException.class).hasMessageContaining("id not found");
    }

    @Test
    void shouldGetCustomer() {
        List<Customer> customerList = service.getAllCustomers();
        assertThat(customerList.size()).isEqualTo(0);
        verify(repository).findAll();
        //verify(repository).deleteAll();
    }

    @Test
    void shouldCreateCustomer() {
        UUID id = UUID.randomUUID();
        Customer customer1 = new Customer(id, "Maryam", "bob@gmail.com");
        service.createCustomer(customer1);

        // then
        ArgumentCaptor<Customer> studentArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(repository).save(studentArgumentCaptor.capture());

        Customer capturedCustomer = studentArgumentCaptor.getValue();
        assertThat(capturedCustomer).isEqualTo(customer1);
    }

    @Test
    void throwWhenEmailTaken() {
        UUID id = UUID.randomUUID();
        Customer customer1 = new Customer(id, "Maryam", "bob@gmail.com");

        given(repository.selectCustomerByEmail("bob@gmail.com")).willReturn(Optional.of(customer1));

        assertThatThrownBy(() -> service.createCustomer(customer1)).isInstanceOf(BadRequestException.class).hasMessageContaining("email exists");
        //given(repository.selectCustomerByEmail(customer1.getEmail())).willReturn(true);
    }


}
