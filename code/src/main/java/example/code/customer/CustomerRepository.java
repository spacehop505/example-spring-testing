package example.code.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@Repository
public interface CustomerRepository  extends JpaRepository<Customer, UUID> {

    @Query(value = "select id, name, email from customer where email = :email", nativeQuery = true)
    Optional<Customer> selectCustomerByEmail(@Param("email") String email);

    @Query(value = "select id, name, email from customer where name = :name", nativeQuery = true)
    List<Customer> selectCustomersByName(@Param("name") String name);


}
