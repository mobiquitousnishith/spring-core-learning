package com.nishith.constants;

public class DatabaseConstants {

    //Tables
    public static final String TBL_PRODUCT = "product";
    public static final String TBL_CURRENCY = "currency";
    public static final String TBL_BRAND = "brand";

    //Columns
    public static final String CLM_NAME = "name";
    public static final String CLM_DESCRIPTION = "description";
    public static final String CLM_PRICE = "price";
    public static final String CLM_BRAND_ID = "brand_id";
    public static final String CLM_CURRENCY_ID = "currency_id";
    public static final String CLM_ID = "id";

    //Queries
    public static final String QRY_BRAND_BY_NAME = "SELECT * FROM brand WHERE name ILIKE ?";
    public static final String QRY_CURRENCY_BY_NAME = "SELECT * FROM currency WHERE name ILIKE ?";
    public static final String QRY_BRAND_LIST_BY_NAME = "SELECT * FROM brand WHERE name IN (:names)";
    public static final String QRY_CURRENCY_LIST_BY_NAME = "SELECT * FROM currency WHERE name IN (:names)";
    public static final String QRY_PRODUCT_LIST_BY_NAME = "SELECT * FROM product WHERE name IN (:names)";
    public static final String QRY_PRODUCTS_EXIST = "SELECT COUNT(*) FROM PRODUCT WHERE name IN (:names)";

    //Inserts
    public static final String INSERT_BRAND = "INSERT INTO brand (name, description) VALUES (?, ?)";
    public static final String INSERT_CURRENCY = "INSERT INTO currency (name, description) VALUES (?, ?)";
    public static final String INSERT_PRODUCT = "INSERT INTO product (name, description, price, brand_id, currency_id) VALUES (?, ?, ?, ?, ?)";

    public static final int BATCH_SIZE = 100;

    private DatabaseConstants() {
        //Prevent instantiation
    }
}
