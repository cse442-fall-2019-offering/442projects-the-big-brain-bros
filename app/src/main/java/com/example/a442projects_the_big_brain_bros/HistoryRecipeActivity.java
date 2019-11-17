package com.example.a442projects_the_big_brain_bros;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HistoryRecipeActivity extends AppCompatActivity {

    //    public TextView textView = (TextView) findViewById(R.id.textV);
    @Override

    //onCreate passed in the parsed JSONarray.  The details are then displayed onto the new activity(Recipe title, Recipe Ingredients, Recipe instructions).
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recipe);
        TextView textView = new TextView(this);
        textView.setTextSize(20);
        String text = HistoryActivity.title +"\n\n"  + "Ingredients: \n";

        for(int i = 0; i < HistoryActivity.ingredientList.size(); i++){
            text += HistoryActivity.ingredientList.get(i) + "\n";
        }
        text += "\n";
        for(int i = 0; i < HistoryActivity.instruction.size(); i++){
            text += "Step " + (i+1) + ")\n" +  HistoryActivity.instruction.get(i) + "\n\n";
        }
        textView.setText(text);
        textView.setMovementMethod(new ScrollingMovementMethod());
        setContentView(textView);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

}

