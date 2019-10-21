package com.example.a442projects_the_big_brain_bros;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
            text += "Step " + (i+1) + ")\n" +  RecipeList.instruction.get(i) + "\n";
        }
        textView.setText(text);
        textView.setMovementMethod(new ScrollingMovementMethod());
        setContentView(textView);
    }
}

