package com.example.tijoj.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.tijoj.bakingapp.data.Recipes;
import com.example.tijoj.bakingapp.ui.fragment.RecipeDetailsFragment;

public class RecipeActivity extends AppCompatActivity{

    public Recipes currRecipe;
    public FragmentManager fragmentManager;

    public boolean isTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        if (savedInstanceState == null) {
            if (findViewById(R.id.recipe_details_fragment_container_tablet) != null) {
                isTwoPane = true;
            }

            fragmentManager = getSupportFragmentManager();

            currRecipe = getIntent().getExtras().getParcelable(Recipes.RECIPE_KEY);

            Bundle bundle = new Bundle();
            bundle.putParcelable(Recipes.RECIPE_KEY, currRecipe);
            bundle.putBoolean("LAYOUT", isTwoPane);

            RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();

            recipeDetailsFragment.setArguments(bundle);

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if (isTwoPane) {
                fragmentTransaction.add(R.id.tablet_main_fragment, recipeDetailsFragment).commit();
            } else {
                fragmentTransaction.add(R.id.recipe_details_fragment_container, recipeDetailsFragment)
                        .commit();
            }

        }
    }

}
