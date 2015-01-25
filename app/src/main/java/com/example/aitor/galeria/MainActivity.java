package com.example.aitor.galeria;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;


public class MainActivity extends Activity {
    private Cursor cursorfotos;
    private GridView gv;
    private Adaptador ad;
    private String[] parameters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gv=(GridView)findViewById(R.id.gvfoto);
        cursorfotos = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, parameters,null,null,null,null);
        ad=new Adaptador(this,cursorfotos);
        gv.setAdapter(ad);


        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MostrarImagen.class);
                Bundle b = new Bundle();
                //Cursor c=(Cursor)lista.getItemAtPosition(position);
                b.putString("ruta", cursorfotos.getString(cursorfotos.getColumnIndex(MediaStore.Images.Media.DATA)));
                b.putInt("id", cursorfotos.getInt(cursorfotos.getColumnIndex(MediaStore.Images.Media._ID)));
                intent.putExtras(b);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
