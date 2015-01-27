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
import android.os.AsyncTask;
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
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class CrearActivity extends Activity {
    public  TextView tvLatitud, tvLongitud, tvDBW;
    public  ImageView ivPicture;
    public  DbManager manager;
    public  Spinner spin;
    public  Button buttAct, buttPicture, buttcreate;
    public  GPSTracker gps;
    public  Uri fileUri;
    public  Bitmap bp;
    public  EditText comentSpace;
    public  Integer globalID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);

        ///////////// Creamos la base de datos
        manager = new DbManager(this);

        ///////// Creamos la intereccion con el GPS y los campos de latitud y longitud
        inicializaGPS();

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

        gps.stopUsingGPS();
        gps = null;

        ///// Boton de actualizar

        buttAct = (Button) findViewById(R.id.btActualizaGps);
        buttAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicializaGPS();
                if(gps.canGetLocation()){
                    tvLatitud.setText(String.valueOf(gps.getLatitude()));//String.valueOf(localizacion.getLatitude()));
                    tvLongitud.setText(String.valueOf(gps.getLongitude()));//String.valueOf(localizacion.getLongitude()));
                }else{
                    tvLatitud.setText("wait for it..");
                    tvLongitud.setText("wait for it..");
                }
                gps.stopUsingGPS();
                gps = null;
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

        comentSpace =  (EditText) findViewById(R.id.comentspace);

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

                    if(lat!=null && lon!=null && tag!=null && uri!=null && com!=null){

                        // comunicar con el servidor y recibir el id_Global
                        new Insertar(CrearActivity.this).execute();


                       /* manager.insertarPropios(lat.floatValue(), lon.floatValue(), tag, uri, com);
                        tvDBW.setText(String.valueOf(manager.getSize()));

                        bp.recycle();
                        bp = null;

                        finalizar();*/
                    }

                }catch(Exception e){    }
            }
        });



    }

    protected void inicializaGPS(){
        gps = new GPSTracker(this);
    }

    protected void finalizar(){
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 ){// Ha tomado la fotografía con éxito
            bp = null;
            try {
                bp = MediaStore.Images.Media.getBitmap(this.getContentResolver(),fileUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ivPicture.setImageBitmap(bp);
            buttPicture.setEnabled(false);
        }


    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        manager.closeDB();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////METODOS DEL WEB SERVICES QUE INSERTAN LOS DATOS A LA BASE DE DATOS EN EL SERVIDOR.
    private boolean setGeopuntos(){
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        //A�adimos nuestros datos
        nameValuePairs = new ArrayList<NameValuePair>(3);
        nameValuePairs.add(new BasicNameValuePair("latitud", tvLatitud.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("longitud",tvLongitud.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("etiqueta",spin.getSelectedItem().toString().trim()));
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        httppost= new HttpPost("http://geocampus.hol.es/register.php"); // Url del Servidor
        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse respuesta_Id = httpclient.execute(httppost);
            String json_string = EntityUtils.toString(respuesta_Id.getEntity());
            JSONObject temp1 = new JSONObject(json_string);

            globalID = new Integer(temp1.getString("id_global"));

            return true;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////LA FUNCION ASYNCRONICA QUE TRABAJA EN BACKGROUND PARA INSERTAR LOS DATOS AL SERVIDOR/////////////
    class Insertar extends AsyncTask<String,String,String> {

        private Activity context;

        Insertar(Activity context){
            this.context=context;
        }
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if(setGeopuntos())
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {

                        Float lat = new Float(tvLatitud.getText().toString());
                        Float lon = new Float(tvLongitud.getText().toString());
                        String tag = new String(spin.getSelectedItem().toString());
                        String uri = new String(fileUri.getPath());
                        String com = new String(comentSpace.getText().toString());

                        manager.insertarPropios(lat.floatValue(), lon.floatValue(), tag, uri, com, globalID.intValue());
                        tvDBW.setText(String.valueOf(manager.getSizePropios()));

                        bp.recycle();
                        bp = null;

                        finalizar();

                        Toast.makeText(context, "Geopunto Insertado con exito", Toast.LENGTH_LONG).show();
                    }
                });
            else
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "Geopunto NO Insertado con Exito", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
