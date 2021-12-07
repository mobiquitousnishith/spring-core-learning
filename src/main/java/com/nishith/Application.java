package com.nishith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.nishith.models.Brand;
import com.nishith.models.Currency;
import com.nishith.models.Product;
import com.nishith.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Application implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(Application.class);

    private final ProductService productService;

    @Autowired
    public Application(ProductService productService) {
        this.productService = productService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        Brand brand = new Brand(null, "HP", "Hewlette Packard");
        Currency currency = new Currency(null, "USD", null);
        Product product = new Product(null, "Pavillion Series 7", "Hybrid Laptop", 999d, brand, currency);
        product = productService.addProduct(product);
        System.out.println(product);
    }

}