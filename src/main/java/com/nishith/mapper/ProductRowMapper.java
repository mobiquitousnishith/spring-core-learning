package com.nishith.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.nishith.models.Brand;
import com.nishith.models.Currency;
import com.nishith.models.Product;

import static com.nishith.constants.DatabaseConstants.CLM_BRAND_ID;
import static com.nishith.constants.DatabaseConstants.CLM_CURRENCY_ID;
import static com.nishith.constants.DatabaseConstants.CLM_DESCRIPTION;
import static com.nishith.constants.DatabaseConstants.CLM_ID;
import static com.nishith.constants.DatabaseConstants.CLM_NAME;
import static com.nishith.constants.DatabaseConstants.CLM_PRICE;

@Component
public class ProductRowMapper implements RowMapper<Product>, ParameterizedPreparedStatementSetter<Product> {

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

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Brand brand = new Brand();
        brand.setId(rs.getLong(CLM_BRAND_ID));
        Currency currency = new Currency();
        currency.setId(rs.getLong(CLM_CURRENCY_ID));
        return new Product(rs.getLong(CLM_ID),
                rs.getString(CLM_NAME),
                rs.getString(CLM_DESCRIPTION),
                rs.getDouble(CLM_PRICE),
                brand, currency);
    }
}
