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
        //create sql query for product_name and description
        String sql = "SELECT * FROM product WHERE product_name LIKE '%" + input + "%' OR description LIKE '%" + input + "%'";
        List<Product> products = em.createNativeQuery(sql, Product.class).getResultList();
        if (products.size() > 0) {
            return products;
        }

        return null;
    }

}
