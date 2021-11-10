package example.code.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("customer")
public class CustomerController {
   // private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    private final CustomerService customerService;
 // log.info("test1");
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomer() {
        return customerService.getAllCustomers();
    }

    @GetMapping
    public ResponseEntity<Customer> getCustomerById(@Param("id") UUID id) {
        Customer customer = customerService.selectCustomersById(id);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @PostMapping
    public ResponseEntity<Response> createCustomer(@RequestBody Customer customer) {
        Response response = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


  //  @DeleteMapping(path = "/student/{studentId}")
 //   public void deleteStudent(@PathVariable("studentId") Long id){
  //      service.deleteStudent(id);
 //   }


   // @PutMapping(path = "/student/{studentId}")
   // public void updateStudent(@PathVariable("studentId") Long id,@RequestBody Student student){
  //      service.updateStudent(id, student);
   // }



}
