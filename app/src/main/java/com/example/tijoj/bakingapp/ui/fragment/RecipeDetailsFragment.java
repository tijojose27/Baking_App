package com.example.tijoj.bakingapp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tijoj.bakingapp.Adapters.StepDetailsAdapter;
import com.example.tijoj.bakingapp.R;
import com.example.tijoj.bakingapp.data.Recipes;
import com.example.tijoj.bakingapp.data.RecipesIngredients;
import com.example.tijoj.bakingapp.helpers.GetIngredientsInfo;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailsFragment extends Fragment {

    public RecyclerView recipeDetailsRecyclerView;

    public Recipes recipe;

    public CardView ingredientsCardView;

    public boolean isTwoPane = false;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);


        recipe = getArguments().getParcelable(Recipes.RECIPE_KEY);
        isTwoPane = getArguments().getBoolean("LAYOUT", false);

        ingredientsCardView = view.findViewById(R.id.card_view_recipe_ingredients);

        ingredientsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateIngredientFragment(recipe);
            }
        });

        recipeDetailsRecyclerView = view.findViewById(R.id.recipe_fragment_details_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recipeDetailsRecyclerView.setLayoutManager(linearLayoutManager);

        recipeDetailsRecyclerView.setAdapter(new StepDetailsAdapter(getContext(), recipe.getRecipesSteps(), isTwoPane));

        return view;
    }

    private void updateIngredientFragment(Recipes currRecipe) {
        Bundle bundle = new Bundle();

        GetIngredientsInfo getIngredientsInfo = new GetIngredientsInfo(currRecipe.getRecipesIngredients());
        bundle.putString(Recipes.RECIPE_NAME, currRecipe.getRecipeName());
        bundle.putStringArray(RecipesIngredients.INGREDIENTS_KEY, getIngredientsInfo.getStuff());

        IngredientsFragment ingredientsFragment = new IngredientsFragment();

        ingredientsFragment.setArguments(bundle);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(isTwoPane){
            fragmentTransaction.replace(R.id.tablet_second_fragment, ingredientsFragment).commit();
        }else {
            fragmentTransaction.replace(R.id.recipe_details_fragment_container, ingredientsFragment)
                    .commit();
        }
    }
}
