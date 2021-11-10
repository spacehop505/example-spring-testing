package example.code.customer;

import org.springframework.stereotype.Component;


public class Response  {

    private Customer customer;
    private Integer status;
    private String message;

    public Response(Customer customer, Integer status, String message) {
        this.customer = customer;
        this.status = status;
        this.message = message;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
