package com.geo.dgpsi.geocampus;

import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MapViewActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private DbManager manager;
    private HashSet<GeoPunto> geopuntosLocales, geopuntosglobales;
    private Spinner spin;
    private CheckBox chkPropios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        setUpMapIfNeeded();

        spin = (Spinner) findViewById(R.id.filtros);
        List<String> list = new ArrayList<String>();
        list.add("Todos");
        list.add("Estudiar");
        list.add("Comer");
        list.add("Deporte");
        list.add("Diversion");
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(dataAdapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                pintaPuntos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });

        chkPropios = (CheckBox)  findViewById(R.id.checkpropios);
        chkPropios.setChecked(true);
        chkPropios.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pintaPuntos();
            }
        });

        manager = new DbManager(this);

        ////// Obtener los puntos propios
        Cursor propioscursor = manager.getAllPropios();
        geopuntosLocales = new HashSet<GeoPunto>();
        // Recorrer el cursor recogiendo los GeoPuntos en una estructura ArrayList

        while(propioscursor.moveToNext()){
            geopuntosLocales.add(new GeoPunto(new Integer(propioscursor.getInt(0)),new Integer(propioscursor.getInt(1)),propioscursor.getFloat(2),
                    propioscursor.getFloat(3),propioscursor.getString(4),propioscursor.getString(5),propioscursor.getString(6)));
        }
        ////// Obtener los puntos globales
        /*
        Cursor globalscursor = manager.getAllGlobals();
        geopuntosglobales = new HashSet<GeoPunto>();
        // Recorrer el cursor recogiendo los GeoPuntos en una estructura ArrayList

        while(globalscursor.moveToNext()){
            geopuntosglobales.add(new GeoPunto(new Integer(0),new Integer(globalscursor.getInt(0)),globalscursor.getFloat(1),
                    globalscursor.getFloat(2),globalscursor.getString(3),"",""));
        }
        */
        pintaPuntos();

    }

    private void pintaPuntos() {
        mMap.clear();
        //HashSet<GeoPunto> cjto;
        if (chkPropios.isChecked()) {
        //    cjto = geopuntosLocales;
        //} else {
        //    cjto = geopuntosglobales;
        //}

            for (GeoPunto gp : geopuntosLocales) {
                float color = BitmapDescriptorFactory.HUE_ROSE;
                if (gp.getEtiqueta().equals("Estudiar")) color = BitmapDescriptorFactory.HUE_BLUE;
                if (gp.getEtiqueta().equals("Comer")) color = BitmapDescriptorFactory.HUE_YELLOW;
                if (gp.getEtiqueta().equals("Deporte")) color = BitmapDescriptorFactory.HUE_GREEN;
                if (gp.getEtiqueta().equals("Diversion")) color = BitmapDescriptorFactory.HUE_RED;

                if (spin.getSelectedItem().toString().equals("Todos") || gp.getEtiqueta().equals(spin.getSelectedItem().toString()))
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(gp.getLongitud(), gp.getLatitud()))
                            .icon(BitmapDescriptorFactory.defaultMarker(color)));
            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        manager.closeDB();
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
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */



}
