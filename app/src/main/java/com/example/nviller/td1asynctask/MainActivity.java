package com.example.nviller.td1asynctask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ListView listF;
    ArrayList<HashMap<String, Object>> films;
    SimpleAdapter simpleAdapter;
    static int i = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listF = (ListView) findViewById(R.id.list_films);
        films = new ArrayList<>();
        String[] from = new String[]{"title", "date", "prod"};
        int[] to = {R.id.title_film, R.id.date_film, R.id.prod_film};

        simpleAdapter = new SimpleAdapter(this, films, R.layout.listfilms, from, to);
        listF.setAdapter(simpleAdapter);

        final Button buttonAdd = (Button) findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("title","Film "+i);
                Date dateNow = new Date();
                map.put("date",dateNow);
                map.put("prod","Producteur "+i);
                films.add(map);
                i++;
                simpleAdapter.notifyDataSetChanged();
            }
        });

        final Button buttonUpdate = (Button) findViewById(R.id.button_update);

        listF.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(), "Changer d'image ici", Toast.LENGTH_SHORT).show();
                //simpleAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }
}
