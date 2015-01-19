package com.geo.dgpsi.geocampus;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.LocationManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;

/** Referencia al gestor de localizacion. */

public class MapViewActivity extends FragmentActivity {
    public static RadioGroup radioAct;
    public static DbManager manager;
    public static GPSTracker gps;
    private LocationManager locationManager;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private SQLiteDatabase db;
    static final LatLng UCM = new LatLng(40.448033, -3.728576);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        setUpMapIfNeeded();
        //Abrir BBDD
        manager = new DbManager(this);

        //////////////Insertar ejemplos//////////////////
        manager.insertar(40.448033, -3.728576,"Estudiar","Ejemplo");
        manager.insertar(40.450670, -3.730658,"Estudiar","Ejemplo");
        manager.insertar(40.450033, -3.733018,"Estudiar","Ejemplo");
        manager.insertar(40.449168, -3.735561,"Estudiar","Ejemplo");
        manager.insertar(40.451552, -3.730947,"Comer","Ejemplo");
        manager.insertar(40.452728, -3.731409,"Deporte","Ejemplo");
        manager.insertar(40.450156, -3.730711,"Diversion","Ejemplo");
        /////////////////////////////////////////////////
        //Filtrado del mapa con los radio button
        radioAct = (RadioGroup) findViewById(R.id.radio_group_list_selector);
        radioAct.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == R.id.radioEstudiar) {
                    //Leer Coordenadas BBDD Local
                    Cursor c = manager.leerFila("Estudiar");
                    int x=0;
                    if (c.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya más registros
                        do {
                            x++;
                            //Toast.makeText(getApplicationContext(),"Ok"+x,Toast.LENGTH_SHORT).show();
                            Double ln = c.getDouble(0);
                            Double lt = c.getDouble(1);
                            //Agregar marcadores al mapa
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(ln, lt))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                        } while(c.moveToNext());
                    }

                } else if (checkedId == R.id.radioComer) {
                    //Leer Coordenadas BBDD Local
                    Cursor c = manager.leerFila("Comer");
                    int x=0;
                    if (c.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya más registros
                        do {
                            x++;
                            //Toast.makeText(getApplicationContext(),"Ok"+x,Toast.LENGTH_SHORT).show();
                            Double ln = c.getDouble(0);
                            Double lt = c.getDouble(1);
                            //Agregar marcadores al mapa
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(ln, lt))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                        } while(c.moveToNext());
                    }

                } else if (checkedId == R.id.radioDeporte) {
                    //Leer Coordenadas BBDD Local
                    Cursor c = manager.leerFila("Deporte");
                    int x=0;
                    if (c.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya más registros
                        do {
                            x++;
                            //Toast.makeText(getApplicationContext(),"Ok"+x,Toast.LENGTH_SHORT).show();
                            Double ln = c.getDouble(0);
                            Double lt = c.getDouble(1);
                            //Agregar marcadores al mapa
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(ln, lt))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                        } while(c.moveToNext());
                    }

                }else if (checkedId == R.id.radioDiver) {
                    //Leer Coordenadas BBDD Local
                    Cursor c = manager.leerFila("Diversion");
                    int x=0;
                    if (c.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya más registros
                        do {
                            x++;
                            //Toast.makeText(getApplicationContext(),"Ok"+x,Toast.LENGTH_SHORT).show();
                            Double ln = c.getDouble(0);
                            Double lt = c.getDouble(1);
                            //Agregar marcadores al mapa
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(ln, lt))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        } while(c.moveToNext());
                    }
                }

            }

        });
    }

   @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapViewActivity}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        gps = new GPSTracker(this);
        if(gps.canGetLocation()){
            mMap.addMarker(new MarkerOptions().position(new LatLng(gps.getLatitude(), gps.getLongitude())).title("Mi posición"));
            CameraPosition position = new CameraPosition.Builder().target(new LatLng(gps.getLatitude(),gps.getLongitude())).zoom(15).build();
            CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
            mMap.animateCamera(update);
        }else{
            Toast.makeText(getApplicationContext(),"No se pudo obtener su ubicación",Toast.LENGTH_LONG).show();
        }
    }
}
