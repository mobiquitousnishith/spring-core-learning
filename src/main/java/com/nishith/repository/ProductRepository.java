package com.nishith.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.nishith.mapper.ProductRowMapper;
import com.nishith.models.Product;

import static com.nishith.constants.DatabaseConstants.BATCH_SIZE;
import static com.nishith.constants.DatabaseConstants.CLM_BRAND_ID;
import static com.nishith.constants.DatabaseConstants.CLM_CURRENCY_ID;
import static com.nishith.constants.DatabaseConstants.CLM_DESCRIPTION;
import static com.nishith.constants.DatabaseConstants.CLM_ID;
import static com.nishith.constants.DatabaseConstants.CLM_NAME;
import static com.nishith.constants.DatabaseConstants.CLM_PRICE;
import static com.nishith.constants.DatabaseConstants.INSERT_PRODUCT;
import static com.nishith.constants.DatabaseConstants.QRY_PRODUCTS_EXIST;
import static com.nishith.constants.DatabaseConstants.QRY_PRODUCT_LIST_BY_NAME;
import static com.nishith.constants.DatabaseConstants.TBL_PRODUCT;

@Repository
public class ProductRepository extends AbstractInventoryRepository {

    private static final String SQL_PRODUCT_EXIST = "SELECT COUNT(*) FROM product where name = ?";

    private final JdbcTemplate jdbcTemplate;

    private final ProductRowMapper productRowMapper;

    private final SimpleJdbcInsert productInsert;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    public ProductRepository(JdbcTemplate jdbcTemplate,
                             ProductRowMapper productRowMapper,
                             NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.productInsert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName(TBL_PRODUCT)
                .usingColumns(CLM_NAME, CLM_DESCRIPTION, CLM_PRICE, CLM_BRAND_ID, CLM_CURRENCY_ID)
                .usingGeneratedKeyColumns(CLM_ID);
        this.productRowMapper = productRowMapper;
    }

    public boolean exists(String productName) {
        return exists(SQL_PRODUCT_EXIST, productName);
    }

    public boolean exists(List<String> productNames) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("names", productNames);
        return namedParameterJdbcTemplate.queryForObject(QRY_PRODUCTS_EXIST, params, Integer.class) > 0;
    }

    public Product addProduct(@NonNull Product product) {
        Map<String, Object> params = productRowMapper.map(product);
        Number primaryKey = productInsert.executeAndReturnKey(params);
        product.setId(primaryKey.longValue());
        return product;
    }

    public int[][] addProducts(@NonNull List<Product> products) {
        return jdbcTemplate.batchUpdate(INSERT_PRODUCT, products, BATCH_SIZE, productRowMapper);
    }

    public List<Product> findProducts(@NonNull List<String> productNames) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("names", productNames);
        return namedParameterJdbcTemplate.queryForStream(QRY_PRODUCT_LIST_BY_NAME, params, productRowMapper)
                .collect(Collectors.toList());
    }
}