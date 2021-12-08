package com.nishith.mapper;

import org.springframework.stereotype.Component;

import com.nishith.models.Brand;
import com.nishith.models.Currency;
import com.nishith.models.Product;

import static com.nishith.constants.DataConveyanceConstants.FIELD_BRAND_DESC;
import static com.nishith.constants.DataConveyanceConstants.FIELD_BRAND_NAME;
import static com.nishith.constants.DataConveyanceConstants.FIELD_CURRENCY_DESC;
import static com.nishith.constants.DataConveyanceConstants.FIELD_CURRENCY_NAME;
import static com.nishith.constants.DataConveyanceConstants.FIELD_PRICE;
import static com.nishith.constants.DataConveyanceConstants.FIELD_PRODUCT_DESC;
import static com.nishith.constants.DataConveyanceConstants.FIELD_PRODUCT_NAME;

@Component
public class DataConveyanceMapper {

    public Product map(String[] columns, String[] data) {
        Currency currency = new Currency();
        Brand brand = new Brand();
        Product product = new Product();
        product.setCurrency(currency);
        product.setBrand(brand);
        for (int i = 0; i < columns.length; i++) {
            switch (columns[i].trim()) {
                case FIELD_PRODUCT_NAME -> product.setName(data[i]);
                case FIELD_PRODUCT_DESC -> product.setDescription(data[i]);
                case FIELD_PRICE -> product.setPrice(Double.valueOf(data[i]));
                case FIELD_BRAND_NAME -> brand.setName(data[i]);
                case FIELD_BRAND_DESC -> brand.setDescription(data[i]);
                case FIELD_CURRENCY_NAME -> currency.setName(data[i]);
                case FIELD_CURRENCY_DESC -> currency.setDescription(data[i]);
                default -> {
                }
            }
        }
        return product;
    }
}
