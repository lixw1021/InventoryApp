package com.xianwei.inventoryapp.data;

import android.provider.BaseColumns;

/**
 * Created by xianwei li on 8/19/2017.
 */

public final class ProductContract {

    public static class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "products";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PRODUCT_IMAGE_URI = "image";
        public static final String COLUMN_PRODUCT_NAME = "name";
        public static final String COLUMN_PRODUCT_QUALITY = "quality";
        public static final String COLUMN_PRODUCT_PRICE = "price";
        public static final String COLUMN_SUPPLIER_PHONE = "phone";

    }

}
