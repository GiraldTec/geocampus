package com.ricom10.geocampus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button btCrear = (Button) findViewById(R.id.btCrear);
        btCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_crear = new Intent();
                intent_crear.setClass(getApplicationContext(),CrearActivity.class);
                startActivity(intent_crear);
            }
        });


        Button btMapa = (Button) findViewById(R.id.btMapa);
        btMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_mapa = new Intent();
                intent_mapa.setClass(getApplicationContext(),MapaActivity.class);
                startActivity(intent_mapa);
            }
        });

        Button btGestionar = (Button) findViewById(R.id.btGestionar);
        btGestionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_gestionar = new Intent();
                intent_gestionar.setClass(getApplicationContext(),GestionarActivity.class);
                startActivity(intent_gestionar);
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
