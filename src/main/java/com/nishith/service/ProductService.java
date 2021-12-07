package com.nishith.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nishith.models.Brand;
import com.nishith.models.Currency;
import com.nishith.models.Product;
import com.nishith.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final BrandService brandService;

    private final CurrencyService currencyService;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          BrandService brandService,
                          CurrencyService currencyService) {
        this.productRepository = productRepository;
        this.brandService = brandService;
        this.currencyService = currencyService;
    }

    public Product addProduct(Product product) {
        resolveBrandByName(product);
        resolveCurrencyByName(product);
        return productRepository.addProduct(product);
    }

    protected void resolveBrandByName(Product product) {
        Optional<Brand> brandOptional = brandService.getBrandByName(product.getBrand().getName());
        Brand brand = brandOptional.orElseGet(() -> brandService.addBrand(product.getBrand()));
        product.setBrand(brand);
    }

    protected void resolveCurrencyByName(Product product) {
        Optional<Currency> currencyOptional = currencyService.getCurrencyByName(product.getCurrency().getName());
        Currency currency = currencyOptional.orElseGet(() -> currencyService.addCurrency(product.getCurrency()));
        product.setCurrency(currency);
    }
}
