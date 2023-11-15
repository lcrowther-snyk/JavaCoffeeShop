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
        //convert input to lowercase
        input = input.toLowerCase(Locale.ROOT);
        //create query using prepared statement to search for products with product_name or description containing input
        var query = em.createNativeQuery("SELECT * FROM product WHERE LOWER(product_name) LIKE ? OR LOWER(description) LIKE ?", Product.class);
        //set parameters
        query.setParameter(1, "%" + input + "%");
        query.setParameter(2, "%" + input + "%");
        //return list of products
        return query.getResultList();

    }

}
