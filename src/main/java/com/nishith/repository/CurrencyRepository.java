package com.nishith.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
import static com.nishith.constants.DatabaseConstants.INSERT_CURRENCY;
import static com.nishith.constants.DatabaseConstants.QRY_CURRENCY_BY_NAME;
import static com.nishith.constants.DatabaseConstants.QRY_CURRENCY_LIST_BY_NAME;
import static com.nishith.constants.DatabaseConstants.TBL_CURRENCY;

@Repository
public class CurrencyRepository extends AbstractInventoryRepository {

    private final JdbcTemplate jdbcTemplate;

    private final CurrencyRowMapper currencyRowMapper;

    private final SimpleJdbcInsert currencyInsert;

    private final CurrencyExtractor currencyExtractor;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CurrencyRepository(JdbcTemplate jdbcTemplate,
                              CurrencyRowMapper currencyRowMapper,
                              CurrencyExtractor currencyExtractor,
                              NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
        this.currencyRowMapper = currencyRowMapper;
        this.currencyExtractor = currencyExtractor;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        currencyInsert = new SimpleJdbcInsert(this.jdbcTemplate).withTableName(TBL_CURRENCY).usingColumns(CLM_NAME, CLM_DESCRIPTION).usingGeneratedKeyColumns(CLM_ID);
    }

    public Optional<Currency> findByName(@NonNull String currencyName) {
        return Optional.ofNullable(jdbcTemplate.query(QRY_CURRENCY_BY_NAME, currencyExtractor, currencyName));
    }

    public Currency addCurrency(Currency currency) {
        Map<String, ?> params = currencyRowMapper.map(currency);
        Number primaryKey = currencyInsert.executeAndReturnKey(params);
        currency.setId(primaryKey.longValue());
        return currency;
    }

    public int[][] addCurrencies(Collection<Currency> currencies) {
        return jdbcTemplate.batchUpdate(INSERT_CURRENCY, currencies, BATCH_SIZE, currencyRowMapper);
    }


    public List<Currency> findCurrencies(List<String> names) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("names", names);
        return namedParameterJdbcTemplate.queryForStream(QRY_CURRENCY_LIST_BY_NAME, params, currencyRowMapper)
                .collect(Collectors.toList());
    }
}
