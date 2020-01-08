package com.example.imagenes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.drm.DrmStore;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    ImageView imagen;
    Button btnBuscar,btnGuardar;
    Canvas c;
    Bitmap copia;

    float x, y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagen=findViewById(R.id.imageView);
        btnBuscar=findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(this);
        btnGuardar=findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(this);

        imagen.setOnTouchListener(this);

        //this.requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},123);
    }

    @Override
    public void onClick(View v) {
        if(v==btnBuscar){
            this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);

            Uri galeria = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            Intent i = new Intent(Intent.ACTION_PICK,galeria);
            i.setType(MediaStore.Images.Media.CONTENT_TYPE);
            startActivityForResult(i,1);
        }else if(v==btnGuardar){
            this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},124);
            //Toast.makeText(this,"ASDASDASDF",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1){
            if (resultCode==RESULT_OK){
                try{
                    Uri uri=data.getData(); //uri de la imagen seleccionada
                    Bitmap foto=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    imagen.setImageBitmap(foto);
                    copia=foto.copy(Bitmap.Config.ARGB_8888,true);
                    c = new Canvas(copia);
                }catch(java.io.IOException ex){
                    ex.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_BUTTON_PRESS){
            x=event.getX();
            y=event.getY();
        }else if(event.getAction()==MotionEvent.ACTION_MOVE){
            Paint p = new Paint();
            p.setColor(Color.BLACK);
            p.setTextSize(200);
            c.drawLine(x,y,event.getX(),event.getY(),p);
            x=event.getX();
            y=event.getY();
            imagen.setImageBitmap(copia);
        }
        return true;
    }
}
