package com.xianwei.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xianwei li on 8/19/2017.
 */

public class EditActivity extends AppCompatActivity {
    @BindView(R.id.detail_image_view)
    ImageView image;
    @BindView(R.id.detail_camera)
    ImageButton imageButton;
    @BindView(R.id.detail_name_tv)
    EditText name;
    @BindView(R.id.detail_quality_et)
    EditText quality;
    @BindView(R.id.detail_price_et)
    EditText price;
    @BindView(R.id.detail_phone_et)
    EditText phone;

    private static final int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
    }

    private void insert() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(filePathColumn[0]));
            cursor.close();

            image.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.detail_camera)
    public void getImageFromGallery () {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,RESULT_LOAD_IMAGE);
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