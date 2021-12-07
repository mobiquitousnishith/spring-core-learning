package com.nishith.mapper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.nishith.models.Product;

import static com.nishith.constants.DatabaseConstants.CLM_BRAND_ID;
import static com.nishith.constants.DatabaseConstants.CLM_CURRENCY_ID;
import static com.nishith.constants.DatabaseConstants.CLM_DESCRIPTION;
import static com.nishith.constants.DatabaseConstants.CLM_NAME;
import static com.nishith.constants.DatabaseConstants.CLM_PRICE;

@Component
public class ProductMapper {

    public Map<String, Object> map(Product product) {
        Map<String, Object> productMap = new HashMap<>();
        productMap.put(CLM_NAME, product.getName());
        productMap.put(CLM_BRAND_ID, product.getBrand().getId());
        productMap.put(CLM_DESCRIPTION, product.getDescription());
        productMap.put(CLM_PRICE, product.getPrice());
        productMap.put(CLM_CURRENCY_ID, product.getCurrency().getId());
        return productMap;
    }
}
