package com.nishith.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nishith.models.Brand;
import com.nishith.models.Product;
import com.nishith.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final BrandService brandService;

    private final CurrencyService currencyService;

    @Autowired
    public ProductService(ProductRepository productRepository, BrandService brandService, CurrencyService currencyService) {
        this.productRepository = productRepository;
        this.brandService = brandService;
        this.currencyService = currencyService;
    }

    public Product addProduct(Product product) {
        resolveBrandByName(product);
        return productRepository.addProduct(product);
    }

    protected void resolveBrandByName(Product product) {
        Optional<Brand> brandOptional = brandService.getBrandByName(product.getBrand().getName());
        brandOptional.ifPresent(product::setBrand);
    }

    protected void resolveCurrencyByName(Product product) {
        currencyService.getCurrencyByName(product.getCurrency().getName());
    }
}
