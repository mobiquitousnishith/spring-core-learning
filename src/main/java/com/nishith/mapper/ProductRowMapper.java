package com.nishith.mapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.nishith.models.Product;

import static com.nishith.constants.DatabaseConstants.CLM_BRAND_ID;
import static com.nishith.constants.DatabaseConstants.CLM_CURRENCY_ID;
import static com.nishith.constants.DatabaseConstants.CLM_DESCRIPTION;
import static com.nishith.constants.DatabaseConstants.CLM_NAME;
import static com.nishith.constants.DatabaseConstants.CLM_PRICE;

@Component
public class ProductRowMapper implements ParameterizedPreparedStatementSetter<Product> {

    public Map<String, Object> map(Product product) {
        Map<String, Object> productMap = new HashMap<>();
        productMap.put(CLM_NAME, product.getName());
        productMap.put(CLM_BRAND_ID, product.getBrand().getId());
        productMap.put(CLM_DESCRIPTION, product.getDescription());
        productMap.put(CLM_PRICE, product.getPrice());
        productMap.put(CLM_CURRENCY_ID, product.getCurrency().getId());
        return productMap;
    }

    @Override
    public void setValues(PreparedStatement ps, Product product) throws SQLException {
        ps.setString(1, product.getName());
        ps.setString(2, product.getDescription());
        ps.setDouble(3, product.getPrice());
        ps.setLong(4, product.getBrand().getId());
        ps.setLong(5, product.getCurrency().getId());
    }
}
