package org.workshop.coffee.repository;

import org.workshop.coffee.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.List;
import java.util.Locale;

@Repository
public class SearchRepository {

    @Autowired
    EntityManager em;

    @Autowired
    DataSource dataSource;

    public List<Product> searchProduct (String input) {
        //create sql query for product_name and description using a prepared statement
        return em.createQuery("select p from Product p where p.productName like :input or p.description like :input", Product.class)
                .setParameter("input", "%" + input + "%")
                .getResultList();

    }

}
