package com.nishith.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.nishith.extractor.CurrencyExtractor;
import com.nishith.mapper.CurrencyRowMapper;
import com.nishith.models.Currency;

import static com.nishith.constants.DatabaseConstants.BATCH_SIZE;
import static com.nishith.constants.DatabaseConstants.CLM_DESCRIPTION;
import static com.nishith.constants.DatabaseConstants.CLM_ID;
import static com.nishith.constants.DatabaseConstants.CLM_NAME;
import static com.nishith.constants.DatabaseConstants.INSERT_BRAND;
import static com.nishith.constants.DatabaseConstants.QRY_CURRENCY_BY_NAME;
import static com.nishith.constants.DatabaseConstants.TBL_CURRENCY;

@Repository
public class CurrencyRepository extends AbstractInventoryRepository {

    private final JdbcTemplate jdbcTemplate;

    private final CurrencyRowMapper currencyRowMapper;

    private final SimpleJdbcInsert currencyInsert;

    private final CurrencyExtractor currencyExtractor;

    public CurrencyRepository(JdbcTemplate jdbcTemplate,
                              CurrencyRowMapper currencyRowMapper,
                              CurrencyExtractor currencyExtractor) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
        this.currencyRowMapper = currencyRowMapper;
        this.currencyExtractor = currencyExtractor;
        currencyInsert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName(TBL_CURRENCY)
                .usingColumns(CLM_NAME, CLM_DESCRIPTION)
                .usingGeneratedKeyColumns(CLM_ID);
    }

    public Optional<Currency> findByName(@NonNull String currencyName) {
        return Optional
                .ofNullable(jdbcTemplate.query(QRY_CURRENCY_BY_NAME, currencyExtractor, currencyName));
    }

    public Currency addCurrency(Currency currency) {
        Map<String, ?> params = currencyRowMapper.map(currency);
        Number primaryKey = currencyInsert.executeAndReturnKey(params);
        currency.setId(primaryKey.longValue());
        return currency;
    }

    public int[][] addCurrencies(List<Currency> currencies) {
        return jdbcTemplate.batchUpdate(INSERT_BRAND, currencies, BATCH_SIZE, currencyRowMapper);
    }

}
