package org.workshop.coffee.controller;

import org.workshop.coffee.domain.Product;
import org.workshop.coffee.repository.SearchRepository;
import org.workshop.coffee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    EntityManager em;

    @Autowired
    DataSource dataSource;
    
    private ProductService productService;

    @Autowired
    public HomeController(ProductService productService, SearchRepository searchRepository) {
        this.productService = productService;
    }

    @GetMapping({"/", "/index", "/home"})
    public String homePage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "index";
    }

    @PostMapping("/")
    public String searchProducts(Model model, @RequestParam String input) {
        model.addAttribute("products", searchProduct(input));
        return "index";
    }
    public List<Product> searchProduct (String input) {
        //create sql query for product_name OR description
        String sql = "SELECT * FROM product WHERE product_name LIKE '%" + input + "%' OR description LIKE '%" + input + "%'";
        //execute query
        List<Product> products = em.createNativeQuery(sql, Product.class).getResultList();
        return products;
    }
}