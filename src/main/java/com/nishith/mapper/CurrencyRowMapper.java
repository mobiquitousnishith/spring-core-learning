package com.nishith.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.nishith.models.Currency;

import static com.nishith.constants.DatabaseConstants.CLM_DESCRIPTION;
import static com.nishith.constants.DatabaseConstants.CLM_ID;
import static com.nishith.constants.DatabaseConstants.CLM_NAME;

@Component
public class CurrencyRowMapper implements RowMapper<Currency> {
    @Override
    public Currency mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Currency(rs.getLong(CLM_ID), rs.getString(CLM_NAME), rs.getString(CLM_DESCRIPTION));
    }

    public Map<String, Object> map(Currency currency) {
        Map<String, Object> currencyMap = new HashMap<>();
        currencyMap.put(CLM_NAME, currency.getName());
        currencyMap.put(CLM_DESCRIPTION, currency.getDescription());
        return currencyMap;
    }
}
