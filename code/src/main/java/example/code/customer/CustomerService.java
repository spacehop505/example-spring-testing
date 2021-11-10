package example.code.customer;

import example.code.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private final EmailValidator emailValidator;


    @Autowired
    public CustomerService(CustomerRepository repository, EmailValidator emailValidator) {
        this.repository = repository;
        this.emailValidator = emailValidator;
    }

    // CREATE
    public Response createCustomer(Customer customer) {
        UUID id = UUID.randomUUID();
        customer.setId(id);
        // Validate Customer
        if (customer.getName() == null || customer.getEmail() == null || customer.getId() == null) {
            throw new BadRequestException("form not valid");
        }

        // Validate Email
        if (!emailValidator.test(customer.getEmail())) {
            throw new BadRequestException("email not valid");
        }


        Optional<Customer> customerOptional = repository.selectCustomerByEmail(customer.getEmail());
        if (customerOptional.isPresent()) {
            throw new BadRequestException("email exists");
        }

        Customer customer1 = repository.save(customer);
        return new Response(customer1, 201, "customer created");
    }

    // READ
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }


    // UPDATE
    //@Transactional
    public void updateCustomer(Customer customer) {
        Optional<Customer> customerOptional = repository.findById(customer.getId());
        if (customerOptional.isPresent()) {
            if (customer.getName() != null && customer.getName().length() > 0 && !customerOptional.get().getName().equals(customer.getName())) {
                customerOptional.get().setName(customer.getName());
            }
            if (customer.getEmail() != null && customer.getEmail().length() > 0 && !customerOptional.get().getEmail().equals(customer.getEmail())) {
                customerOptional.get().setEmail(customer.getEmail());
            }
            repository.save(customerOptional.get());
        } else {
            throw new BadRequestException("not found");
        }
    }

    public void deleteCustomer(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new BadRequestException("id not found");
        }
    }


    public Customer selectCustomersById(UUID id) {

        Optional<Customer> optionalCustomer =  repository.findById(id);
        if (optionalCustomer.isPresent()){
            return optionalCustomer.get();
        }else{
            throw new BadRequestException("not found");
        }
    }


}
