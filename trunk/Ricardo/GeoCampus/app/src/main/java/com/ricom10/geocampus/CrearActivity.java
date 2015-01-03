package com.ricom10.geocampus;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class CrearActivity extends Activity {

    TextView tvLatitud;
    TextView tvLongitud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);

        tvLatitud = (TextView) findViewById(R.id.tvLatitud);
        tvLongitud = (TextView)findViewById(R.id.tvLongitud);

        LocationManager locManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        final Location localizacion = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (localizacion!=null){
            tvLatitud.setText(String.valueOf(localizacion.getLatitude()));
            tvLongitud.setText(String.valueOf(localizacion.getLongitude()));
        }

        LocationListener locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                tvLatitud.setText(String.valueOf(localizacion.getLatitude()));
                tvLongitud.setText(String.valueOf(localizacion.getLongitude()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000*5,10,locListener );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crear, menu);
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
