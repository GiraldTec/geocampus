package com.geo.dgpsi.geocampus;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class CrearActivity extends Activity {
    public static TextView tvLatitud;
    public static TextView tvLongitud;
    public static DbManager database;
    public static Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);

        ///////////// Creamos la base de datos
        database = new DbManager(this);

        ///////// Creamos la intereccion con el GPS y los campos de latitud y longitud
        tvLatitud = (TextView) findViewById(R.id.tvLatitud);
        tvLongitud = (TextView)findViewById(R.id.tvLongitud);

        LocationManager locManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        final Location localizacion = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (localizacion!=null){
            tvLatitud.setText(String.valueOf(localizacion.getLatitude()));
            tvLongitud.setText(String.valueOf(localizacion.getLongitude()));
        }else{
            tvLatitud.setText("wait for it..");
            tvLongitud.setText("wait for it..");
        }

        LocationListener locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                tvLatitud.setText(String.valueOf(localizacion.getLatitude()));
                tvLongitud.setText(String.valueOf(localizacion.getLongitude()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000*5,10,locListener );

        //////// Creamos la interaccion con las etiquetas
        spin = (Spinner) findViewById(R.id.etiquetas);
        List<String> list = new ArrayList<String>();
        list.add("Estudiar");
        list.add("Comer");
        list.add("Deporte");
        list.add("Diversion");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(dataAdapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}
