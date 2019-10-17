package com.example.a442projects_the_big_brain_bros;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeList extends AppCompatActivity {

    public ArrayList<String> dishes;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);
        updateView();
    }

    private void updateView(){
        ListView listView = (ListView) findViewById((R.id.listv));
        String[] items = {};
        dishes = new ArrayList<>(Arrays.asList(items));
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.txtitem, dishes);
        listView.setAdapter(adapter);
    }
}

