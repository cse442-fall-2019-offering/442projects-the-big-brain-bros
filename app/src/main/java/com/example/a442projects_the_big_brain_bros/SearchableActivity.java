package com.example.a442projects_the_big_brain_bros;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SearchableActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
//        Intent intent = getIntent();
//        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        // Verifies intent action and get the query
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void doMySearch(String query) {
        if(!query.isEmpty()) {
            Intent intent = new Intent(this, RecipeList.class);
            startActivity(intent);
        }
    }


}
