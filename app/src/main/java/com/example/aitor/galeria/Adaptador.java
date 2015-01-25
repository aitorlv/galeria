package com.example.aitor.galeria;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by aitor on 23/01/2015.
 */
public class Adaptador extends CursorAdapter {

    public Adaptador(Context context, Cursor c) {
        super(context, c,true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ImageView v = new ImageView(context);
        v.setLayoutParams(new GridView.LayoutParams(100, 100));
        v.setScaleType(ImageView.ScaleType.FIT_CENTER);
        bindView(v, context, cursor);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView img = (ImageView) view;
        String ruta =cursor.getString(cursor.getColumnIndex("_data"));
        Picasso.with(context).load(new File(ruta)).resize(200,200).centerCrop().into(img);
    }

}
