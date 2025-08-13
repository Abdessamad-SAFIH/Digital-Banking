package org.enset.ebankigbackend.repositories;

import org.enset.ebankigbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepeository extends JpaRepository<Customer, Long> {
    //List<Customer> findByNameContains(String keyword);
    @Query("select c from Customer c where c.name like :kw")
    List<Customer> search(@Param("kw") String keyword);

}
