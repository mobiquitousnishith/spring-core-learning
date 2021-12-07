package com.nishith.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.nishith.models.Currency;

import static com.nishith.constants.DatabaseConstants.CLM_DESCRIPTION;
import static com.nishith.constants.DatabaseConstants.CLM_ID;
import static com.nishith.constants.DatabaseConstants.CLM_NAME;

@Component
public class CurrencyExtractor implements ResultSetExtractor<Currency> {

    @Override
    public Currency extractData(ResultSet rs) throws SQLException, DataAccessException {
        Currency currency = null;
        while (rs.next()) {
            currency = new Currency(rs.getLong(CLM_ID), rs.getString(CLM_NAME), rs.getString(CLM_DESCRIPTION));
        }
        return currency;
    }
}
