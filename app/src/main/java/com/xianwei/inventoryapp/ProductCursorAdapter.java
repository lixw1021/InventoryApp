package com.xianwei.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xianwei.inventoryapp.data.ProductContract.ProductEntry;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xianwei li on 8/24/2017.
 */

public class ProductCursorAdapter extends CursorAdapter {
    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        String productName = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_NAME));
        viewHolder.nameTextView.setText(productName);

        String productPrice = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_PRICE));
        viewHolder.priceTextView.setText(productPrice);

        String productQuality = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_QUALITY));
        viewHolder.qualityTextView.setText(productQuality);

        long id = cursor.getLong(cursor.getColumnIndexOrThrow(ProductEntry._ID));
        Uri updatedUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id);
        Log.i("12345", updatedUri.toString());

        viewHolder.saleButton.setOnClickListener(new View.OnClickListener() {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(ProductEntry._ID));
            Uri updatedUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id);
            @Override
            public void onClick(View v) {
                int quality = Integer.parseInt(viewHolder.qualityTextView.getText().toString());
                if (quality > 0) {
                    viewHolder.qualityTextView.setText(String.valueOf(--quality));
                    Log.i("12345", updatedUri.toString());
                    ContentValues values = new ContentValues();
                    values.put(ProductEntry.COLUMN_PRODUCT_QUALITY, String.valueOf(quality));
                    int rows = context.getContentResolver().update(updatedUri,values, null, null);
                    Log.i("12345 rowa", String.valueOf(rows));
                }
            }
        });

    }

    static class ViewHolder {
        @BindView(R.id.item_name_tv)
        TextView nameTextView;
        @BindView(R.id.item_price_tv)
        TextView priceTextView;
        @BindView(R.id.item_quality_tv)
        TextView qualityTextView;
        @BindView(R.id.item_sale_bt)
        Button saleButton;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
