package workshop3.business;

import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import workshop3.model.Customer;

/**
 *
 * @author nirav
 */
@Stateless
public class CustomerBean {
    
    @PersistenceContext private EntityManager em;
    
    public Optional<Customer> findById(Integer custId){
        TypedQuery<Customer> query = 
                em.createNamedQuery("Customer.findByCustomerId", Customer.class);
        query.setParameter("customerId", custId);
       
        List<Customer> result = query.getResultList();
        
        if(result.isEmpty()) return(Optional.empty());
        
        return(Optional.of(result.get(0)));
                
    } 
    
    public boolean insertCust (Customer customer){
        em.persist(customer);
        return true;
    }
}
