package com.nishith.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.nishith.mapper.DataConveyanceMapper;
import com.nishith.models.Brand;
import com.nishith.models.Currency;
import com.nishith.models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.nishith.constants.DataConveyanceConstants.FIELD_LIST;
import static com.nishith.constants.DataConveyanceConstants.SPLIT_REGEX;

@Service
public class DataConveyanceService {

    private final static Logger LOGGER = LoggerFactory.getLogger(DataConveyanceService.class);
    private final DataConveyanceMapper dataConveyanceMapper;
    private final ProductService productService;
    private final BrandService brandService;
    private final CurrencyService currencyService;

    @Autowired
    public DataConveyanceService(DataConveyanceMapper dataConveyanceMapper,
                                 ProductService productService,
                                 BrandService brandService,
                                 CurrencyService currencyService) {
        this.dataConveyanceMapper = dataConveyanceMapper;
        this.productService = productService;
        this.brandService = brandService;
        this.currencyService = currencyService;
    }

    public void importData(@NonNull File file) {
        List<Product> productsImport = getProducts(file);
        validateProducts(productsImport);
        importBrands(productsImport);
        importCurrency(productsImport);
        productService.addProducts(productsImport);
    }

    private void validateProducts(List<Product> productImport) {
        if (productService.exists(productImport.stream().map(Product::getName).toList())) {
            throw new IllegalArgumentException("Some of the products already exist. Import failed.");
        }
    }

    private void importBrands(List<Product> products) {
        Set<Brand> brands = products.stream().map(Product::getBrand).collect(Collectors.toSet());
        LOGGER.debug("Total [{}] brands found", brands.size());
        List<String> brandNames = brands.stream().map(Brand::getName).collect(Collectors.toList());
        List<Brand> existingBrands = brandService.getBrandsByName(brandNames);
        List<String> existingBrandNames = existingBrands.stream().map(Brand::getName).toList();
        brands.removeIf(brand -> existingBrandNames.contains(brand.getName()));
        brandNames.removeAll(existingBrandNames);
        LOGGER.debug("Total [{}] brands to be imported", brands.size());
        if (!brands.isEmpty()) {
            brandService.addBrands(brands);
            existingBrands.addAll(brandService.getBrandsByName(brandNames));
        }
        products.forEach(product -> product.setBrand(existingBrands
                .stream()
                .filter(brand -> product.getBrand().getName().equals(brand.getName())).findFirst().get()));
    }

    private void importCurrency(List<Product> products) {
        Set<Currency> currencies = products.stream().map(Product::getCurrency).collect(Collectors.toSet());
        LOGGER.debug("Total [{}] currencies found", currencies.size());
        List<String> currencyNames = currencies.stream().map(Currency::getName).collect(Collectors.toList());
        List<Currency> existingCurrencies = currencyService.getCurrenciesByName(currencyNames);
        List<String> existingCurrencyNames = existingCurrencies.stream().map(Currency::getName).toList();
        currencies.removeIf(brand -> existingCurrencyNames.contains(brand.getName()));
        currencyNames.removeIf(existingCurrencyNames::contains);
        LOGGER.debug("Total [{}] currencies to be imported", currencies.size());
        if (!currencies.isEmpty()) {
            currencyService.addCurrencies(currencies);
            existingCurrencies.addAll(currencyService.getCurrenciesByName(currencyNames));
        }
        products.forEach(product -> product.setCurrency(existingCurrencies
                .stream()
                .filter(currency -> product.getCurrency().getName().equals(currency.getName())).findFirst().get()));
    }

    private void importProducts(List<Product> products) {

    }

    private List<Product> getProducts(@NonNull File file) {
        List<Product> productsImport = new ArrayList<>();
        String columnLine;
        String dataLine;
        String[] data;
        String[] columns;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            columnLine = reader.readLine();
            columns = extractColumns(columnLine);
            while ((dataLine = reader.readLine()) != null) {
                data = extractData(columns.length, dataLine);
                productsImport.add(dataConveyanceMapper.map(columns, data));
            }
            LOGGER.debug("Size of the imported data: [{}]", productsImport.size());
        } catch (IOException e) {
            LOGGER.error("File import failed.", e);
        }
        return productsImport;
    }

    private String[] extractColumns(String columnCsv) {
        String[] columns = columnCsv.split(SPLIT_REGEX);
        for (String column : columns) {
            if (!FIELD_LIST.contains(column)) {
                throw new IllegalArgumentException(String.format("[%s] not found in the file.", column));
            }
        }
        return columns;
    }

    private String[] extractData(int size, String dataLine) {
        String[] data = dataLine.split(SPLIT_REGEX);
        if (data.length < size || data.length > size) {
            throw new IllegalArgumentException("Inconsistent data file");
        }
        return data;
    }
}
