package example.code;

import example.code.customer.Customer;
import example.code.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeApplication.class, args);
	}

	/*@Bean
	CommandLineRunner commandLineRunner(CustomerRepository customerRepository){
		return args ->{
			Customer customer = new Customer();
			customerRepository.save(customer);

		};
	}
*/
}
