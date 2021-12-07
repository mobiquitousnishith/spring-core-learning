package com.nishith.repository;

import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.nishith.mapper.CurrencyRowMapper;
import com.nishith.models.Currency;

import static com.nishith.constants.DatabaseConstants.CLM_DESCRIPTION;
import static com.nishith.constants.DatabaseConstants.CLM_ID;
import static com.nishith.constants.DatabaseConstants.CLM_NAME;
import static com.nishith.constants.DatabaseConstants.QRY_CURRENCY_BY_NAME;
import static com.nishith.constants.DatabaseConstants.TBL_CURRENCY;

@Repository
public class CurrencyRepository extends AbstractInventoryRepository {

    private final JdbcTemplate jdbcTemplate;

    private final CurrencyRowMapper currencyRowMapper;

    private final SimpleJdbcInsert currencyInsert;

    public CurrencyRepository(JdbcTemplate jdbcTemplate, CurrencyRowMapper currencyRowMapper) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
        this.currencyRowMapper = currencyRowMapper;
        currencyInsert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName(TBL_CURRENCY)
                .usingColumns(CLM_NAME, CLM_DESCRIPTION)
                .usingGeneratedKeyColumns(CLM_ID);
    }

    public Optional<Currency> findByName(@NonNull String currencyName) {
        return Optional
                .ofNullable(jdbcTemplate.queryForObject(QRY_CURRENCY_BY_NAME, currencyRowMapper, currencyName));
    }

    public Currency addCurrency(Currency currency) {
        Map<String, ?> params = currencyRowMapper.map(currency);
        Number primaryKey = currencyInsert.executeAndReturnKey(params);
        currency.setId(primaryKey.longValue());
        return currency;
    }

}
