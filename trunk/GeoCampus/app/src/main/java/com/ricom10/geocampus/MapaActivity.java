package com.ricom10.geocampus;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MapaActivity extends Activity {


    Button btVerde;
    Button btAmarillo;
    Button btRojo;
    Button btAzul;
    TextView tvColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        btVerde = (Button)findViewById(R.id.btVerde);
        btAmarillo = (Button)findViewById(R.id.btAmarillo);
        btRojo = (Button)findViewById(R.id.btRojo);
        btAzul = (Button)findViewById(R.id.btAzul);
        tvColor = (TextView)findViewById(R.id.tvColor);


        btAzul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvColor.setText("Azul");
            }
        });

        btVerde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvColor.setText("Verde");
            }
        });
        btAmarillo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvColor.setText("Amarillo");
            }
        });
        btRojo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvColor.setText("Rojo");
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mapa, menu);
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
