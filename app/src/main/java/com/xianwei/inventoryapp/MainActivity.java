package com.xianwei.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.xianwei.inventoryapp.data.ProductContract.ProductEntry;
import com.xianwei.inventoryapp.data.ProductDbHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.empty_list_view)
    View emptyView;
    @BindView(R.id.floating_add)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        listView.setEmptyView(emptyView);
        String[] projection = new String[] {ProductEntry._ID,
                                            ProductEntry.COLUMN_PRODUCT_NAME,
                                            ProductEntry.COLUMN_PRODUCT_PRICE,
                                            ProductEntry.COLUMN_PRODUCT_QUALITY};

        Cursor cursor = getContentResolver().query(ProductEntry.CONTENT_URI, projection, null, null, null);
        ProductCursorAdapter adapter = new ProductCursorAdapter(this, cursor);
        listView.setAdapter(adapter);

    }

    @OnItemClick(R.id.list_view)
    void openEdit(long id) {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        Uri uri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id);
        intent.putExtra("URI",uri.toString());
        startActivity(intent);
    }

    @OnClick(R.id.floating_add)
    public void addItem(View view) {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        startActivity(intent);
    }
}
