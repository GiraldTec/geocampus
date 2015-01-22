package com.geo.dgpsi.geocampus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;


public class GestionActivity extends ActionBarActivity {

    public ArrayList<GeoPunto> geopuntosLocales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion);

        //coger toda la base de datos local y meterla en geopuntosLocales
        DbManager manager = new DbManager(this);
        Cursor cursor = manager.getAllPropios();
        geopuntosLocales = new ArrayList<GeoPunto>();
        // Recorrer el cursor recogiendo los GeoPuntos en una estructura ArrayList
        int j = 0;
        while(cursor.moveToNext()){
            geopuntosLocales.add(new GeoPunto(new Integer(cursor.getInt(0)),new Integer(cursor.getInt(1)),cursor.getFloat(2),
                    cursor.getFloat(3),cursor.getString(4),cursor.getString(5),cursor.getString(6)));
        }
        // Construyendo el Adapter con la lista de geoPuntos
        GeoPuntoAdapter geoAdapter = new GeoPuntoAdapter(this,R.layout.geopunto_layout,geopuntosLocales);

        ListView listaPuntos = (ListView) findViewById(R.id.geoPuntosList);
        listaPuntos.setAdapter(geoAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gestion, menu);
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




    private class GeoPuntoAdapter extends ArrayAdapter<GeoPunto> {
        private final Context context;
        private final ArrayList<GeoPunto> puntos;


        public GeoPuntoAdapter(Context context, int resource, ArrayList<GeoPunto> puntos) {
            super(context, resource, puntos);
            this.context = context;
            this.puntos = puntos;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final GeoPunto gp = this.puntos.get(position);


            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.geopunto_layout, parent, false);

            TextView etiqueta = (TextView) rowView.findViewById(R.id.tvEtiqueta);
            etiqueta.setText(gp.getEtiqueta());

            Button verFoto = (Button) rowView.findViewById(R.id.btGaleria);
            Button eliminar = (Button) rowView.findViewById(R.id.btEliminar);
            TextView comentario = (TextView) rowView.findViewById(R.id.tvComentario);
            comentario.setText(gp.getComentario());

            verFoto.setOnClickListener(new View.OnClickListener() {
                GeoPunto g = gp;
                @Override
                public void onClick(View v) {

                    // TODO abrir un intent de Galería



                    File dir = new File(g.getUri());
                    Intent intent = new Intent();
                    intent.setAction(android.content.Intent.ACTION_VIEW);
                    intent.setData(Uri.fromFile(dir));
                    startActivity(intent);

                }
            });

            eliminar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //TODO eliminar la fila de la tabla local

                }
            });


            return rowView;
        }



    }

}
