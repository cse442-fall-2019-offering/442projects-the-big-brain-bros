package com.example.a442projects_the_big_brain_bros;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RecipeActivity extends AppCompatActivity {

    //    public TextView textView = (TextView) findViewById(R.id.textV);
    @Override

    //onCreate passed in the parsed JSONarray.  The details are then displayed onto the new activity(Recipe title, Recipe Ingredients, Recipe instructions).
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recipe);
        TextView textView = new TextView(this);
        textView.setTextSize(20);
        String text = RecipeList.title +"\n\n"  + "Ingredients: \n";

        for(int i = 0; i < RecipeList.ingredientList.size(); i++){
            text += RecipeList.ingredientList.get(i) + "\n";
        }
        text += "\n";
        for(int i = 0; i < RecipeList.instruction.size(); i++){
            text += "Step " + (i+1) + ")\n" +  RecipeList.instruction.get(i) + "\n\n";
        }
        textView.setText(text);
        textView.setMovementMethod(new ScrollingMovementMethod());
        setContentView(textView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.recipe_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save_button) {
            // do something here
        }
        return super.onOptionsItemSelected(item);
    }

}

