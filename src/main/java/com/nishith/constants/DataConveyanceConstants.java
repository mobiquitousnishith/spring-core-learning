package com.nishith.constants;

import java.util.List;

public class DataConveyanceConstants {

    public static final String FIELD_PRODUCT_NAME = "productName";
    public static final String FIELD_PRODUCT_DESC = "productDescription";
    public static final String FIELD_PRICE = "price";
    public static final String FIELD_BRAND_NAME = "brandName";
    public static final String FIELD_BRAND_DESC = "brandDescription";
    public static final String FIELD_CURRENCY_NAME = "currencyName";
    public static final String FIELD_CURRENCY_DESC = "currencyDescription";

    public static final List<String> FIELD_LIST = List.of(FIELD_PRODUCT_NAME,
            FIELD_PRODUCT_DESC,
            FIELD_PRICE,
            FIELD_BRAND_NAME,
            FIELD_BRAND_DESC,
            FIELD_CURRENCY_NAME,
            FIELD_CURRENCY_DESC);
    public static final String SPLIT_REGEX = "([\\s]*,[\\s]*)";

    private DataConveyanceConstants() {
        //Prevent Instantiation
    }
}
