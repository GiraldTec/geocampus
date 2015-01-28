package com.geo.dgpsi.geocampus;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    public  DbManager manager;
    public  Integer globalID;
    public JSONArray responseJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b;

        b = (Button) findViewById(R.id.menu_button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getApplicationContext(),MapViewActivity.class);
                startActivity(i);
            }
        });

        b = (Button) findViewById(R.id.menu_button1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getApplicationContext(),CrearActivity.class);
                startActivity(i);
            }
        });

        b = (Button) findViewById(R.id.menu_button4);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getApplicationContext(),GestionActivity.class);
                startActivity(i);
            }
        });

        b = (Button) findViewById(R.id.menu_button3);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button aux = (Button) findViewById(R.id.menu_button3);
                aux.setEnabled(false);
                manager = new DbManager(MainActivity.this);
                new Actualizar(MainActivity.this).execute();
            }
        });


    }

    private boolean getGeopuntos(){
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        //A�adimos nuestros datos
        nameValuePairs = new ArrayList<NameValuePair>(3);

        long size = manager.getSizeGlobals();
        if(size==0) size=-1;

        nameValuePairs.add(new BasicNameValuePair("id_global", ""+size));
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        httppost= new HttpPost("http://geocampus.hol.es/update.php"); // Url del Servidor
        try {

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse respuesta_Id = httpclient.execute(httppost);
            String json_string = EntityUtils.toString(respuesta_Id.getEntity());
            if(!json_string.equals("")) {
                String jsonArray = json_string.replace("true", "\"true\":");
                JSONObject jsonResponse = new JSONObject("{" + jsonArray + "}");
                responseJson = jsonResponse.getJSONArray("true");
                return true;
            }else{
                return false;
            }
            //responseJson = new JSONObject(json_string);


        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        } catch (ClientProtocolException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    class Actualizar extends AsyncTask<String,String,String> {

        private Activity context;

        Actualizar(Activity context){
            this.context=context;
        }
        @Override
        protected String doInBackground(String... params) {

            if(getGeopuntos()) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // TODO Recorrer el responseJson
                        JSONObject aux;
                        for (int i = 0; i < responseJson.length(); i++) {
                            try {
                                aux = responseJson.getJSONObject(i);
                                int idglobal =  aux.getInt("id_global");
                                Double latitud = aux.getDouble("latitud");
                                Double longitud = aux.getDouble("longitud");
                                String etiqueta = aux.getString("label");
                                manager.insertarGlobals(longitud.floatValue(),latitud.floatValue(),etiqueta,idglobal);
                                long cap = manager.getSizeGlobals();
                                cap = cap + 1 ;
                            }
                            catch(Exception e){}
                        }

                        manager.closeDB();
                        Button auxB = (Button) findViewById(R.id.menu_button3);
                        auxB.setEnabled(true);

                        Toast.makeText(context, "Geopuntos recuperados", Toast.LENGTH_LONG).show();
                    }
                });
            }
            else
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "Geopuntos NO recuperados", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        try{
            manager.closeDB();
        }catch(Exception e){}
    }
}
