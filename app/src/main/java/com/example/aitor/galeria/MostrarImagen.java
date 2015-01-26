package com.example.aitor.galeria;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

public class MostrarImagen extends Activity implements View.OnClickListener {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 400;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;
    private ImageView imageView;
    private int id;
    private String[] parameters;
    private int filasCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_imagen);
        /* ... */

        // Gesture detection
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(MostrarImagen.this);
        imageView.setOnTouchListener(gestureListener);
        if(savedInstanceState!=null){
            id=savedInstanceState.getInt("id");
            Uri uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Integer.toString(id));
            Picasso.with(MostrarImagen.this).load(uri).into(imageView);
        }else {
            Bundle b = getIntent().getExtras();
            id = b.getInt("id");
            Picasso.with(this).load(new File(b.get("ruta").toString())).into(imageView);

        }

    }



    @Override
    public void onClick(View v) {
        //Filter f = (Filter) v.getTag();
        //FilterFullscreenActivity.show(this, input, f);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("id",id);
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.v("llamo","entrooo");
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                    filasCursor=getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, parameters,null,null,null,null).getCount();
                    if(id<=filasCursor) {
                        id++;
                        Uri uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Integer.toString(id));
                        Picasso.with(MostrarImagen.this).load(uri).into(imageView);
                    }else {
                        Toast.makeText(MostrarImagen.this,"Ultima foto",Toast.LENGTH_SHORT).show();
                    }
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                    if(id>=1373) {
                        id--;
                        Uri uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Integer.toString(id));
                        Picasso.with(MostrarImagen.this).load(uri).into(imageView);
                    }else {
                        Toast.makeText(MostrarImagen.this,"Es la primera imagen",Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }


        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }
}