package com.nishith.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.nishith.models.Brand;

import static com.nishith.constants.DatabaseConstants.CLM_DESCRIPTION;
import static com.nishith.constants.DatabaseConstants.CLM_ID;
import static com.nishith.constants.DatabaseConstants.CLM_NAME;

@Component
public class BrandExtractor implements ResultSetExtractor<Brand> {
    @Override
    public Brand extractData(ResultSet rs) throws SQLException, DataAccessException {
        Brand brand = null;
        if (rs.next()) {
            brand = new Brand(rs.getLong(CLM_ID), rs.getString(CLM_NAME), rs.getString(CLM_DESCRIPTION));
        }
        return brand;
    }
}
