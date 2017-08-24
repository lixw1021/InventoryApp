package com.xianwei.inventoryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xianwei.inventoryapp.data.ProductContract.ProductEntry;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.floating_add)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        insertTest();
        readTest();
    }

    private void readTest() {
        String[] projection = { ProductEntry._ID,
                                ProductEntry.COLUMN_PRODUCT_NAME,
                                ProductEntry.COLUMN_PRODUCT_IMAGE_URI,
                                ProductEntry.COLUMN_PRODUCT_PRICE,
                                ProductEntry.COLUMN_PRODUCT_QUALITY,
                                ProductEntry.COLUMN_SUPPLIER_PHONE};


        Cursor cursor = getContentResolver().query(ProductEntry.CONTENT_URI, projection, null, null, null);
        if (cursor == null) {
            Log.i("12345", "cursor = null");
        }
        TextView tv = (TextView) findViewById(R.id.test);
        tv.setText("id --- name --- image --- price --- quality --- phone");
        while (cursor.moveToNext()) {
            Long id = cursor.getLong(cursor.getColumnIndexOrThrow(ProductEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_NAME));
            String image = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_IMAGE_URI));
            int price = cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_PRICE));
            int quality = cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_QUALITY));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_SUPPLIER_PHONE));
            tv.append("\n" + "  " + id + "  " + name + "  " + image + "  " + price + "  " + quality + "  " + phone);
        }

    }

    private void insertTest() {
        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_PRODUCT_NAME, "product name");
        values.put(ProductEntry.COLUMN_PRODUCT_IMAGE_URI, "image uri");
        values.put(ProductEntry.COLUMN_PRODUCT_PRICE, 10);
        values.put(ProductEntry.COLUMN_PRODUCT_QUALITY, 100);
        values.put(ProductEntry.COLUMN_SUPPLIER_PHONE, "4129990992");

        Uri uri = getContentResolver().insert(ProductEntry.CONTENT_URI, values);
        Log.i("database insert", uri.toString());
    }

    @OnClick(R.id.floating_add)
    public void addItem(View view) {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        startActivity(intent);
    }
}
