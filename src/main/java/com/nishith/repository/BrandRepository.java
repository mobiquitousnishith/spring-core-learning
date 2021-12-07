package com.nishith.repository;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.nishith.extractor.BrandExtractor;
import com.nishith.mapper.BrandRowMapper;
import com.nishith.models.Brand;

import static com.nishith.constants.DatabaseConstants.CLM_DESCRIPTION;
import static com.nishith.constants.DatabaseConstants.CLM_ID;
import static com.nishith.constants.DatabaseConstants.CLM_NAME;
import static com.nishith.constants.DatabaseConstants.QRY_BRAND_BY_NAME;
import static com.nishith.constants.DatabaseConstants.TBL_BRAND;

@Repository
public class BrandRepository extends AbstractInventoryRepository {

    private final JdbcTemplate jdbcTemplate;

    private final BrandRowMapper brandRowMapper;

    private final SimpleJdbcInsert brandInsert;

    private final BrandExtractor brandExtractor;

    @Autowired
    public BrandRepository(JdbcTemplate jdbcTemplate, BrandRowMapper brandRowMapper, BrandExtractor brandExtractor) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
        this.brandRowMapper = brandRowMapper;
        brandInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TBL_BRAND)
                .usingColumns(CLM_NAME, CLM_DESCRIPTION)
                .usingGeneratedKeyColumns(CLM_ID);
        this.brandExtractor = brandExtractor;
    }

    public Optional<Brand> findByName(@NonNull String brandName) {
        return Optional.ofNullable(jdbcTemplate.query(QRY_BRAND_BY_NAME, brandExtractor, brandName));
    }

    public Brand addBrand(Brand brand) {
        Map<String, ?> params = brandRowMapper.map(brand);
        Number primaryKey = brandInsert.executeAndReturnKey(params);
        brand.setId(primaryKey.longValue());
        return brand;
    }
}
