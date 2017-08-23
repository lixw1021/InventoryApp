package com.xianwei.inventoryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xianwei.inventoryapp.data.ProductContract.ProductEntry;
import com.xianwei.inventoryapp.data.ProductDbHelper;

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

        insertTest();
        readTest();
    }

    private void readTest() {
        ProductDbHelper dbhelper = new ProductDbHelper(this);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String[] projection = { ProductEntry._ID,
                                ProductEntry.COLUMN_PRODUCT_NAME,
                                ProductEntry.COLUMN_PRODUCT_IMAGE_URI,
                                ProductEntry.COLUMN_PRODUCT_PRICE,
                                ProductEntry.COLUMN_PRODUCT_QUALITY,
                                ProductEntry.COLUMN_SUPPLIER_PHONE};
        Cursor cursor = db.query(ProductEntry.TABLE_NAME, projection, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Long id = cursor.getLong(cursor.getColumnIndexOrThrow(ProductEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_NAME));
            String image = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_IMAGE_URI));
            int price = cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_PRICE));
            int quality = cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_QUALITY));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_SUPPLIER_PHONE));

            TextView tv = (TextView) findViewById(R.id.test);
            tv.setText("id --- name --- image --- price --- quality --- phone");
            tv.append("\n" + "  " + id + "  " + name + "  " + image + "  " + price + "  " + quality + "  " + phone);
        }

    }

    private void insertTest() {
        ProductDbHelper dphelper = new ProductDbHelper(this);
        SQLiteDatabase db = dphelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_PRODUCT_NAME, "product name");
        values.put(ProductEntry.COLUMN_PRODUCT_IMAGE_URI, "image uri");
        values.put(ProductEntry.COLUMN_PRODUCT_PRICE, 10);
        values.put(ProductEntry.COLUMN_PRODUCT_QUALITY, 100);
        values.put(ProductEntry.COLUMN_SUPPLIER_PHONE, "4129990992");

        long id = db.insert(ProductEntry.TABLE_NAME,null, values);
        Log.i("database insert", String.valueOf(id));
    }

    @OnClick(R.id.floating_add)
    public void addItem(View view) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        startActivity(intent);
    }
}
