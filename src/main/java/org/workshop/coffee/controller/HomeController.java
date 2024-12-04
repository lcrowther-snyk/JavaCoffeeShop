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
        //create a string query using product_name and description
        String query = "SELECT * FROM Product p WHERE p.productName LIKE '%" + input + "%' OR p.description LIKE '%" + input + "%'";
        //create a query
        return em.createNativeQuery(query, Product.class).getResultList();
    }
}