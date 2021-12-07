package com.nishith.repository;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.nishith.mapper.ProductMapper;
import com.nishith.models.Brand;
import com.nishith.models.Product;

import static com.nishith.constants.DatabaseConstants.CLM_BRAND_ID;
import static com.nishith.constants.DatabaseConstants.CLM_CURRENCY_ID;
import static com.nishith.constants.DatabaseConstants.CLM_DESCRIPTION;
import static com.nishith.constants.DatabaseConstants.CLM_ID;
import static com.nishith.constants.DatabaseConstants.CLM_NAME;
import static com.nishith.constants.DatabaseConstants.CLM_PRICE;
import static com.nishith.constants.DatabaseConstants.TBL_PRODUCT;

@Repository
public class ProductRepository extends AbstractInventoryRepository {

    private static final String SQL_PRODUCT_EXIST = "SELECT COUNT(*) FROM product where name = ?";

    private final JdbcTemplate jdbcTemplate;

    private final ProductMapper productMapper;

    private final BrandRepository brandRepository;

    private final SimpleJdbcInsert productInsert;


    @Autowired
    public ProductRepository(JdbcTemplate jdbcTemplate, ProductMapper productMapper, BrandRepository brandRepository) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
        this.productInsert = new SimpleJdbcInsert(this.jdbcTemplate).withTableName(TBL_PRODUCT).usingColumns(CLM_NAME, CLM_DESCRIPTION, CLM_PRICE, CLM_BRAND_ID, CLM_CURRENCY_ID).usingGeneratedKeyColumns(CLM_ID);
        this.productMapper = productMapper;
        this.brandRepository = brandRepository;
    }

    public boolean exists(String productName) {
        return exists(SQL_PRODUCT_EXIST, productName);
    }

    public Product addProduct(@NonNull Product product) {
        Map<String, Object> params = productMapper.map(product);
        Number primaryKey = productInsert.executeAndReturnKey(params);
        product.setId(primaryKey.longValue());
        return product;
    }

}
