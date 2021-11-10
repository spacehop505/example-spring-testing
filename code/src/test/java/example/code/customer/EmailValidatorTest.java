package example.code.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {


    private final EmailValidator emailValidator = new EmailValidator();


    @Test
    void test(){
       boolean yellow =  emailValidator.test("fff@gmail.com");
        assertTrue(yellow);
    }

}