/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workshop3.rest;

import java.util.Optional;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import workshop3.business.CustomerBean;
import workshop3.model.Customer;

/**
 * @author nirav
 */
@RequestScoped
@Path("/customer")
public class CustomerHandler {
    
    @EJB private CustomerBean custBean;
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response post(MultivaluedMap<String, String> formValue){
        Integer custId = 0;
        Integer credit = 0;
        
        custId = Integer.parseInt(formValue.getFirst("custId"));
        credit = Integer.parseInt(formValue.getFirst("credit"));
        
        Customer cust = new Customer();
        cust.setName(formValue.getFirst("name"));
        cust.setAddressline1(formValue.getFirst("addr1"));
        cust.setAddressline2(formValue.getFirst("addr2"));
        cust.setCity(formValue.getFirst("city"));
        cust.setCreditLimit(credit);
        cust.setCustomerId(custId);
        cust.setEmail(formValue.getFirst("email"));
        cust.setFax(formValue.getFirst("fax"));
        cust.setPhone(formValue.getFirst("phone"));
        cust.setState(formValue.getFirst("state"));
        cust.setDiscount(formValue.getFirst("discount"));
        cust.setZip(formValue.getFirst("zip"));

        
        custBean.insertCust(cust);
        return (Response.status(Response.Status.ACCEPTED).build());
        
    }
    
    @Path("{cid}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("cid")Integer cid){
        Optional<Customer> customer = custBean.findById(cid);
        
        if(!customer.isPresent()){
            return(Response.status(Response.Status.NOT_FOUND).build());
        } else {
            Customer cust = customer.get();
            return (Response.ok(cust.toJSON()).build());
        }
    }
}
