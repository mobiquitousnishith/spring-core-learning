package com.nishith.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.nishith.extractor.BrandExtractor;
import com.nishith.mapper.BrandRowMapper;
import com.nishith.models.Brand;

import static com.nishith.constants.DatabaseConstants.BATCH_SIZE;
import static com.nishith.constants.DatabaseConstants.CLM_DESCRIPTION;
import static com.nishith.constants.DatabaseConstants.CLM_ID;
import static com.nishith.constants.DatabaseConstants.CLM_NAME;
import static com.nishith.constants.DatabaseConstants.INSERT_BRAND;
import static com.nishith.constants.DatabaseConstants.QRY_BRAND_BY_NAME;
import static com.nishith.constants.DatabaseConstants.QRY_BRAND_LIST_BY_NAME;
import static com.nishith.constants.DatabaseConstants.TBL_BRAND;

@Repository
public class BrandRepository extends AbstractInventoryRepository {

    private final JdbcTemplate jdbcTemplate;

    private final BrandRowMapper brandRowMapper;

    private final SimpleJdbcInsert brandInsert;

    private final BrandExtractor brandExtractor;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public BrandRepository(JdbcTemplate jdbcTemplate,
                           BrandRowMapper brandRowMapper,
                           BrandExtractor brandExtractor,
                           NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
        this.brandRowMapper = brandRowMapper;
        brandInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TBL_BRAND)
                .usingColumns(CLM_NAME, CLM_DESCRIPTION)
                .usingGeneratedKeyColumns(CLM_ID);
        this.brandExtractor = brandExtractor;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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

    public int[][] addBrands(Collection<Brand> brands) {
        return jdbcTemplate.batchUpdate(INSERT_BRAND, brands, BATCH_SIZE, brandRowMapper);
    }

    public List<Brand> findBrands(List<String> names) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("names", names);
        return namedParameterJdbcTemplate.queryForStream(QRY_BRAND_LIST_BY_NAME, params, brandRowMapper)
                .collect(Collectors.toList());
    }

}
