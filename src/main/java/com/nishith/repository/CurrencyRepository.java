package com.nishith.repository;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.nishith.mapper.CurrencyRowMapper;
import com.nishith.models.Currency;

import static com.nishith.constants.DatabaseConstants.QRY_CURRENCY_BY_NAME;

@Repository
public class CurrencyRepository extends AbstractInventoryRepository {

    private final JdbcTemplate jdbcTemplate;

    private final CurrencyRowMapper currencyRowMapper;

    public CurrencyRepository(JdbcTemplate jdbcTemplate, CurrencyRowMapper currencyRowMapper) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
        this.currencyRowMapper = currencyRowMapper;
    }

    public Optional<Currency> findByName(@NonNull String currencyName) {
        return Optional
                .ofNullable(jdbcTemplate.queryForObject(QRY_CURRENCY_BY_NAME, currencyRowMapper, currencyName));
    }
}
