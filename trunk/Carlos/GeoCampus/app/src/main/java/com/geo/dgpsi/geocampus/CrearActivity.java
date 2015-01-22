package com.geo.dgpsi.geocampus;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CrearActivity extends Activity {
    public static TextView tvLatitud, tvLongitud, tvDBW;
    public static ImageView ivPicture;
    public static DbManager manager;
    public static Spinner spin;
    public static Button buttAct, buttPicture, buttcreate;
    public static GPSTracker gps;
    public static Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);

        ///////////// Creamos la base de datos
        manager = new DbManager(this);

        ///////// Creamos la intereccion con el GPS y los campos de latitud y longitud
        gps = new GPSTracker(this);

        tvLatitud = (TextView) findViewById(R.id.tvLatitud);
        tvLongitud = (TextView)findViewById(R.id.tvLongitud);

        //LocationManager locManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        //final Location localizacion = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(gps.canGetLocation()){
            tvLatitud.setText(String.valueOf(gps.getLatitude()));//String.valueOf(localizacion.getLatitude()));
            tvLongitud.setText(String.valueOf(gps.getLongitude()));//String.valueOf(localizacion.getLongitude()));
        }else{
            tvLatitud.setText("wait for it..");
            tvLongitud.setText("wait for it..");
        }

        ///// Boton de actualizar

        buttAct = (Button) findViewById(R.id.btActualizaGps);
        buttAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gps.canGetLocation()){
                    tvLatitud.setText(String.valueOf(gps.getLatitude()));//String.valueOf(localizacion.getLatitude()));
                    tvLongitud.setText(String.valueOf(gps.getLongitude()));//String.valueOf(localizacion.getLongitude()));
                }else{
                    tvLatitud.setText("wait for it..");
                    tvLongitud.setText("wait for it..");
                }
            }
        });

        //////// Creamos la interaccion con las etiquetas
        spin = (Spinner) findViewById(R.id.etiquetas);
        List<String> list = new ArrayList<String>();
        list.add("Estudiar");
        list.add("Comer");
        list.add("Deporte");
        list.add("Diversion");

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(dataAdapter);

        ////////// Rellenar comentario(30)

        final EditText comentSpace =  (EditText) findViewById(R.id.comentspace);

        ////////// Funcionalidad de tomar una foto

        ivPicture = (ImageView) findViewById(R.id.ivPicture);

        buttPicture = (Button) findViewById(R.id.btPicture);
        buttPicture.setOnClickListener(new View.OnClickListener() {
            private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    fileUri = FileSaver.getOutputMediaFileUri(1); // create a file to save the image
                    takePictureIntent.putExtra("return-data",true);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
                    startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }
                //ivPicture.setText(fileUri.getPath());


            /*
                // create Intent to take a picture and return control to the calling application
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                fileUri = FileSaver.getOutputMediaFileUri(1); // create a file to save the image
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

                // start the image capture Intent
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);*/
            }

        });

                ////////// Funcionalidad de crear un geopunto BOTON+CONTADOR

        tvDBW = (TextView) findViewById(R.id.dbwindow);

        buttcreate = (Button) findViewById(R.id.btCreate);
        buttcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Float lat = new Float(tvLatitud.getText().toString());
                    Float lon = new Float(tvLongitud.getText().toString());
                    String tag = new String(spin.getSelectedItem().toString());
                    String uri = new String(fileUri.getPath());
                    String com = new String(comentSpace.getText().toString());

                    //TODO comunicar con el servidor y recibir el id_Global

                    if(lat!=null && lon!=null && tag!=null && uri!=null && com!=null){
                        manager.insertarPropios(lat.floatValue(),lon.floatValue(),tag,uri,com);
                        tvDBW.setText(String.valueOf(manager.getSize()));
                        finish();
                    }

                }catch(Exception e){    }
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 ){// Ha tomado la fotografía con éxito
            Bitmap bp = null;
            try {
                bp = MediaStore.Images.Media.getBitmap(this.getContentResolver(),fileUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ivPicture.setImageBitmap(bp);
            buttPicture.setEnabled(false);
        }


    }
}
