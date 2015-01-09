package com.geo.dgpsi.geocampus;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class CrearActivity extends Activity {
    public static TextView tvLatitud, tvLongitud, tvDBW;
    public static DbManager manager;
    public static Spinner spin;
    public static Button buttAct, buttcreate;
    public static GPSTracker gps;

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



        ////////// Funcionalidad de crear un geopunto

        tvDBW = (TextView) findViewById(R.id.dbwindow);


        buttcreate = (Button) findViewById(R.id.btCreate);
        buttcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // TODO arreglar el fallo de no tener coordenadas (y q no se joda el casting....)
                Float lat = new Float(tvLatitud.getText().toString());
                Float lon = new Float(tvLongitud.getText().toString());
                String tag = new String(spin.getSelectedItem().toString());
                String com = new String(comentSpace.getText().toString());

                manager.insertar(lat.floatValue(),lon.floatValue(),tag,com);
                tvDBW.setText(String.valueOf(manager.getSize()));
                //finish();
            }
        });



    }

}
