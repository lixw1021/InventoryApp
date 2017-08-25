package com.xianwei.inventoryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.xianwei.inventoryapp.data.ProductContract.ProductEntry;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xianwei li on 8/19/2017.
 */

public class EditActivity extends AppCompatActivity {
    @BindView(R.id.detail_image_view)
    ImageView imageView;
    @BindView(R.id.detail_camera)
    ImageButton imageButton;
    @BindView(R.id.detail_name_tv)
    EditText nameEditView;
    @BindView(R.id.detail_quality_et)
    EditText qualityEditView;
    @BindView(R.id.detail_quality_add)
    ImageButton qualityAddButton;
    @BindView(R.id.detail_quality_minus)
    ImageButton qualityMinusButton;
    @BindView(R.id.detail_price_et)
    EditText priceEditView;
    @BindView(R.id.detail_phone_et)
    EditText phoneEditView;
    @BindView(R.id.detail_order_bt)
    Button orderButton;
    @BindView(R.id.detail_delete_bt)
    ImageButton deleteButton;

    private static final int RESULT_LOAD_IMAGE = 1;

    private String productName;
    private Uri productImageUri;
    private String productQuality;
    private String productPrice;
    private String supplierPhone;
    private String itemUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);

        itemUri = getIntent().getStringExtra("URI");
        if (itemUri != null) {
            setupUI(Uri.parse(itemUri));
        } else {
            deleteButton.setVisibility(View.GONE);
        }
    }

    private void setupUI(Uri itemUri) { String[] project = {ProductEntry.COLUMN_PRODUCT_IMAGE_URI,
                                            ProductEntry.COLUMN_PRODUCT_NAME,
                                            ProductEntry.COLUMN_PRODUCT_QUALITY,
                                            ProductEntry.COLUMN_PRODUCT_PRICE,
                                            ProductEntry.COLUMN_SUPPLIER_PHONE};

        Cursor cursor = getContentResolver().query(itemUri, project, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String imageUriString = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_IMAGE_URI));
            if (imageUriString != null) {
                productImageUri = Uri.parse(imageUriString);
                imageView.setImageBitmap(getBitmapFromUri(productImageUri));
            }

            productName = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_NAME));
            productQuality = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_QUALITY));
            productPrice = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_PRICE));
            supplierPhone = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_SUPPLIER_PHONE));

            nameEditView.setText(productName);
            qualityEditView.setText(productQuality);
            priceEditView.setText(productPrice);
            phoneEditView.setText(supplierPhone);
      }
    }

    private void saveData() {
        productName = nameEditView.getText().toString().trim();
        productQuality = qualityEditView.getText().toString();
        supplierPhone = phoneEditView.getText().toString();
        if (priceEditView.getText().toString().length() != 0 ){
            productPrice = priceEditView.getText().toString();
        } else {
            productPrice = "0";
        }

        ContentValues values = new ContentValues();
        if (productName.length() == 0) {
            Toast.makeText(this, "Please add a product name", Toast.LENGTH_LONG).show();
            return;
        }
        if (productImageUri != null) {
            values.put(ProductEntry.COLUMN_PRODUCT_IMAGE_URI, productImageUri.toString());
        }
        if (supplierPhone != null) {
            values.put(ProductEntry.COLUMN_SUPPLIER_PHONE, supplierPhone);

        }
        values.put(ProductEntry.COLUMN_PRODUCT_NAME, productName);
        values.put(ProductEntry.COLUMN_PRODUCT_QUALITY, productQuality);
        values.put(ProductEntry.COLUMN_PRODUCT_PRICE, productPrice);

        getContentResolver().insert(ProductEntry.CONTENT_URI, values);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            productImageUri = data.getData();
            Bitmap bitmap = getBitmapFromUri(productImageUri);
            imageView.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public Bitmap getBitmapFromUri(Uri imageUri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(filePathColumn[0]));
            cursor.close();
            return BitmapFactory.decodeFile(imagePath);
        }
        return null;
    }

    @OnClick(R.id.detail_camera)
    public void getImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    @OnClick(R.id.detail_quality_add)
    public void qualityAdd() {
        int displayNumber = Integer.parseInt(qualityEditView.getText().toString());
        if (displayNumber < Integer.MAX_VALUE){
            qualityEditView.setText(String.valueOf(++displayNumber));
        }
    }

    @OnClick(R.id.detail_quality_minus)
    public void qualityMinus() {
        int displayNumber = Integer.parseInt(qualityEditView.getText().toString());
        if (displayNumber > 0) {
            qualityEditView.setText(String.valueOf(--displayNumber));
        }
    }

    @OnClick(R.id.detail_order_bt)
    public void makeCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        String phoneNumber = parsePhone(phoneEditView.getText().toString());
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    private String parsePhone(String phoneString) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < phoneString.length(); i++) {
            char phoneChar = phoneString.charAt(i);
            if (phoneChar >= '0' && phoneChar <= '9' ) {
                result.append(phoneChar);
            }
        }
        return result.toString();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_save:
                saveData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}