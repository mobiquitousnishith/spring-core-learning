package com.nishith.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.nishith.models.Brand;
import com.nishith.models.Currency;

import static com.nishith.constants.DatabaseConstants.CLM_DESCRIPTION;
import static com.nishith.constants.DatabaseConstants.CLM_ID;
import static com.nishith.constants.DatabaseConstants.CLM_NAME;

@Component
public class BrandRowMapper implements RowMapper<Brand> {

    @Override
    public Brand mapRow(ResultSet rs, int rowNum) throws SQLException {
        Brand brand = new Brand(rs.getLong(CLM_ID), rs.getString(CLM_NAME), rs.getString(CLM_DESCRIPTION));
        return brand;
    }

    public Map<String, Object> map(Brand brand) {
        Map<String, Object> currencyMap = new HashMap<>();
        currencyMap.put(CLM_NAME, brand.getName());
        currencyMap.put(CLM_DESCRIPTION, brand.getDescription());
        return currencyMap;
    }
}
