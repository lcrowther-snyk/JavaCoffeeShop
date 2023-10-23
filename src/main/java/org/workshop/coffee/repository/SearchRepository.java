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

    public List<Product> searchProduct(String input) {
        //create native query with bind parameter for input
        var nativeQuery = em.createNativeQuery("SELECT * FROM product WHERE product_name LIKE ?1", Product.class);
        //set input parameter
        nativeQuery.setParameter(1, "%" + input + "%");
        return nativeQuery.getResultList();
    }

}
