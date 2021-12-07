package com.nishith.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.nishith.mapper.BrandRowMapper;
import com.nishith.models.Brand;

import static com.nishith.constants.DatabaseConstants.QRY_BRAND_BY_NAME;

@Repository
public class BrandRepository extends AbstractInventoryRepository {

    private final JdbcTemplate jdbcTemplate;

    private final BrandRowMapper brandRowMapper;

    @Autowired
    public BrandRepository(JdbcTemplate jdbcTemplate, BrandRowMapper brandRowMapper) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
        this.brandRowMapper = brandRowMapper;
    }

    public Optional<Brand> findByName(@NonNull String brandName) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(QRY_BRAND_BY_NAME, brandRowMapper, brandName));
    }
}
