package com.example.a442projects_the_big_brain_bros;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;

public class RecipeList extends AppCompatActivity {

    public ArrayList<String> dishes;
    private ArrayAdapter<String> adapter;
//    public static ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
                updateView();
//            }
//        }, 2000);


    }

    private void updateView() {
//        ListView listView = (ListView) findViewById((R.id.listv));
//        String[] items = {};
//        dishes = new ArrayList<>(Arrays.asList(items));
//        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.txtitem, dishes);
//        listView.setAdapter(adapter);
        ArrayList<String> titleList = new ArrayList<String>();
        ListView listView = (ListView) findViewById(R.id.listv);

//        final ArrayList<String> titleList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, SearchActivity.titleList);

        listView.setAdapter(arrayAdapter);


    }
}


