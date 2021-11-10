package example.code.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//@DataJpaTest(properties = {"spring.jpa.properties.javax.persistence.validation.mode=none"})
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @AfterEach
    public void tearDown(){
        repository.deleteAll();
    }

    @Test
    @DisplayName("YELLLOWWWWWWWWWWWWWWWWWW")
    public void shouldSelectByEmail() {
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id,"Bob", "Bob@gmail.com");
        repository.save(customer);

        Optional<Customer> optionalCustomer = repository.selectCustomerByEmail(customer.getEmail());
        assertThat(optionalCustomer).isPresent();
        assertEquals(optionalCustomer.get().getId(), customer.getId());
        assertEquals(optionalCustomer.get().getName(), customer.getName());
        assertEquals(optionalCustomer.get().getEmail(), customer.getEmail());
    }

    @Test
    public void shouldNotSelectByEmail() {
        String email = "Bob@gmail.com";
        Optional<Customer> optionalCustomer = repository.selectCustomerByEmail(email);
        assertThat(optionalCustomer).isNotPresent();
    }

    @Test
    public void shouldSaveCustomer() {
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id,"Bob", "Bob@gmail.com");
        repository.save(customer);

        Optional<Customer> optionalCustomer = repository.findById(id);
        assertThat(optionalCustomer).isPresent();
        assertEquals(optionalCustomer.get().getId(), customer.getId());
        assertEquals(optionalCustomer.get().getName(), customer.getName());
        assertEquals(optionalCustomer.get().getEmail(), customer.getEmail());
        assertThat(optionalCustomer.get().getId()).isEqualTo(customer.getId());
    }

    @Test
    public void shouldSelectByName() {
        Customer customer1 = new Customer(UUID.randomUUID(),"Bob", "Bob1@gmail.com");
        Customer customer2 = new Customer(UUID.randomUUID(),"Bob1", "Bob2@gmail.com");
        Customer customer3 = new Customer(UUID.randomUUID(),"Bob", "Bob3@gmail.com");

        repository.save(customer1);
        repository.save(customer2);
        repository.save(customer3);
        List<Customer> optionalCustomer = repository.selectCustomersByName("Bob");
        Long countCustomerNameBob = optionalCustomer.stream().filter(item -> "Bob".equals(item.getName())).count();
       // assertTrue(optionalCustomer.stream().anyMatch(item -> "Bob".equals(item.getName())));
        assertThat(countCustomerNameBob).isEqualTo(2L);
    }


}


