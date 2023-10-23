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
        //search proucts by product_name
        return em.createQuery("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE :input", Product.class)
                .setParameter("input", "%" + input.toLowerCase(Locale.ROOT) + "%")
                .getResultList();

    }

}
