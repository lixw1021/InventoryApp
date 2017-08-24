package com.xianwei.inventoryapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xianwei li on 8/19/2017.
 */

public class EditActivity extends AppCompatActivity {
    @BindView(R.id.detail_image_view)
    ImageView image;
    @BindView(R.id.detail_name_tv)
    EditText name;
    @BindView(R.id.detail_quality_et)
    EditText quality;
    @BindView(R.id.detail_price_et)
    EditText price;
    @BindView(R.id.detail_phone_et)
    EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
    }

    private void insert() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_save:
                insert();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}