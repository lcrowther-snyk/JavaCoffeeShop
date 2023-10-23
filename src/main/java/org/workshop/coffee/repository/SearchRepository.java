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
        //crete sql query using input to search for product_name or description
        String sql = "SELECT * FROM product WHERE LOWER(product_name) LIKE '%" + input.toLowerCase() + "%' OR LOWER(description) LIKE '%" + input.toLowerCase() + "%'";
        //create native query
        List<Product> products = em.createNativeQuery(sql, Product.class).getResultList();
        return products;

    }

}
