package com.example.nviller.td1asynctask;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ListView listF;
    ArrayList<HashMap<String, Object>> films;
    SimpleAdapter simpleAdapter;
    static int i = 1;
    ImageView img;
    private String imgUrl = "http://lorempixel.com/200/200";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listF = (ListView) findViewById(R.id.list_films);
        films = new ArrayList<>();
        String[] from = new String[]{"title", "date", "prod","img"};
        int[] to = {R.id.title_film, R.id.date_film, R.id.prod_film, R.id.img_film};

        simpleAdapter = new CustomAdapter(this, films, R.layout.listfilms, from, to);
        listF.setAdapter(simpleAdapter);

        final Button buttonAdd = (Button) findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                HashMap<String, Object> map = new HashMap<>();
                DownloadImageTask downloadImage = new DownloadImageTask();
                map.put("title", "Film " + i);
                Date dateNow = new Date();
                map.put("date", dateNow);
                map.put("prod", "Producteur " + i);
                map.put("img",downloadImage.execute(imgUrl));
                films.add(map);
                i++;
                simpleAdapter.notifyDataSetChanged();
            }
        });

        final Button buttonUpdate = (Button) findViewById(R.id.button_update);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Fonctionnalité pas encore prête", Toast.LENGTH_SHORT).show();
            }
        });

        listF.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(), "Changer d'image ici", Toast.LENGTH_SHORT).show();
                //simpleAdapter.notifyDataSetChanged();
                return true;
            }
        });


    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... stringUrl) {

            Log.i(this.getClass().getCanonicalName(), "Entre doInBackground");

            InputStream input = null;
            HttpURLConnection connection = null;
            Bitmap bitmap = null;

            try {
                URL url = new URL(stringUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                input = new BufferedInputStream(connection.getInputStream());
                bitmap = BitmapFactory.decodeStream(input);

            } catch (Exception e) {
                Log.e(this.getClass().getCanonicalName(), "Erreur lors de la création de l'image");
            } finally {
                try {
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                    Log.e(this.getClass().getCanonicalName(), "Erreur input");
                }
                if (connection != null)
                    connection.disconnect();
            }
            Log.i(this.getClass().getCanonicalName(), "Quitte doInBackground");
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

           Log.i(this.getClass().getCanonicalName(), "Entre onPostExecute");
        }
    }


}
