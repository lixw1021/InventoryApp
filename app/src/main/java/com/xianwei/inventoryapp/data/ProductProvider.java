package com.xianwei.inventoryapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xianwei.inventoryapp.data.ProductContract.ProductEntry;

/**
 * Created by xianwei li on 8/23/2017.
 */

public class ProductProvider extends ContentProvider {

    private static final int PRODUCT = 100;
    private static final int PRODUCT_ID = 101;
    private ProductDbHelper productDbHelper;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(ProductContract.CONTENT_AUTHORITY, ProductContract.PATH_PRODUCT, PRODUCT);
        uriMatcher.addURI(ProductContract.CONTENT_AUTHORITY, ProductContract.PATH_PRODUCT + "/#", PRODUCT_ID);
    }

    @Override
    public boolean onCreate() {
        productDbHelper = new ProductDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = productDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = uriMatcher.match(uri);
        Log.i("12345", String.valueOf(match));
        switch (match) {
            case PRODUCT:
                cursor = db.query(ProductEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PRODUCT_ID:
                selection = ProductEntry._ID + "=?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                cursor = db.query(ProductEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Can't query unknown URI" + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int match = uriMatcher.match(uri);
        switch (match) {
            case PRODUCT:
                return insertProduct(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertProduct(Uri uri, ContentValues values) {
//        String name = values.getAsString(ProductEntry.COLUMN_PRODUCT_NAME);
//        if (name == null) {
//            Toast.makeText(getContext(), "Please input a product name", Toast.LENGTH_LONG).show();
//        }
//        String imageUri = values.getAsString(ProductEntry.COLUMN_PRODUCT_IMAGE_URI);
//        int quality = values.getAsInteger(ProductEntry.COLUMN_PRODUCT_QUALITY);
//        int price = values.getAsInteger(ProductEntry.COLUMN_PRODUCT_PRICE);
//        String phone = values.getAsString(ProductEntry.COLUMN_SUPPLIER_PHONE);

        SQLiteDatabase db = productDbHelper.getWritableDatabase();
        long id = db.insert(ProductEntry.TABLE_NAME, null, values);
        Uri insertUri = ContentUris.withAppendedId(uri, id);
        return insertUri;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        switch (match) {
            case PRODUCT:
                return updateProduct(uri, values, selection, selectionArgs);
            case PRODUCT_ID:
                selection = ProductEntry._ID + "=?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                return updateProduct(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateProduct(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//        String name = values.getAsString(ProductEntry.COLUMN_PRODUCT_NAME);
//        String imageUri = values.getAsString(ProductEntry.COLUMN_PRODUCT_IMAGE_URI);
//        int price = values.getAsInteger(ProductEntry.COLUMN_PRODUCT_PRICE);
//        int quality = values.getAsInteger(ProductEntry.COLUMN_PRODUCT_QUALITY);
//        String phone = values.getAsString(ProductEntry.COLUMN_SUPPLIER_PHONE);

        SQLiteDatabase db = productDbHelper.getWritableDatabase();
        int rowsUpdated = db.update(ProductEntry.TABLE_NAME, values, selection, selectionArgs);
        return rowsUpdated;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = productDbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case PRODUCT:
                rowsDeleted = db.delete(ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PRODUCT_ID:
                selection = ProductEntry._ID + "=?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                rowsDeleted = db.delete(ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for" + uri);
        }
        return rowsDeleted;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
