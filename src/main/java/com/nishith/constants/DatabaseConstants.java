package com.nishith.constants;

public class DatabaseConstants {

    public static final String TBL_PRODUCT = "product";
    public static final String CLM_NAME = "name";
    public static final String CLM_DESCRIPTION = "description";
    public static final String CLM_PRICE = "price";
    public static final String CLM_BRAND_ID = "brand_id";
    public static final String CLM_CURRENCY_ID = "currency_id";
    public static final String CLM_ID = "id";

    public static final String QRY_BRAND_BY_NAME = "SELECT * FROM brand WHERE name ilike ?";

    public static final String QRY_CURRENCY_BY_NAME = "SELECT * FROM brand WHERE name ilike ?";

    private DatabaseConstants() {
        //Prevent instantiation
    }
}
