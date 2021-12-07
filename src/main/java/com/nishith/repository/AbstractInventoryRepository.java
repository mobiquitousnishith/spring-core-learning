package com.nishith.repository;

import org.springframework.jdbc.core.JdbcTemplate;

public abstract class AbstractInventoryRepository {

    private final JdbcTemplate jdbcTemplate;

    protected AbstractInventoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean exists(String sql, Object... args) {
        int result = jdbcTemplate.queryForObject(sql, Integer.class, args);
        return result > 0;
    }
}
