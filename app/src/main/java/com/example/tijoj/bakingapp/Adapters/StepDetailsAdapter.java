package com.example.tijoj.bakingapp.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tijoj.bakingapp.R;
import com.example.tijoj.bakingapp.RecipeActivity;
import com.example.tijoj.bakingapp.data.Recipes;
import com.example.tijoj.bakingapp.data.RecipesSteps;
import com.example.tijoj.bakingapp.ui.fragment.StepsDetailFragment;

import java.util.ArrayList;

/**
 * Created by tijoj on 2/23/2018.
 */

public class StepDetailsAdapter extends RecyclerView.Adapter<StepDetailsAdapter.RecipeDetailsViewHolder>{

    private ArrayList<RecipesSteps> recipesSteps;

    private Context context;

    private boolean isTwoPane;

    private FragmentManager fragmentManager;

    public StepDetailsAdapter(Context context, ArrayList<RecipesSteps> recipesSteps, boolean isTwoPane) {
        this.recipesSteps = recipesSteps;
        this.context = context;
        //CHECKING IF ITS A TABLET LAYOUT
        this.isTwoPane = isTwoPane;
    }


    @Override
    public StepDetailsAdapter.RecipeDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.simple_cards, null);

        return new RecipeDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepDetailsAdapter.RecipeDetailsViewHolder holder, final int position) {

        final RecipesSteps currRecipeSteps = recipesSteps.get(position);

        holder.cardTextView.setText(currRecipeSteps.getShortDescription());

        holder.cardTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentManager = ((RecipeActivity)context).getSupportFragmentManager();

                StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();

                Bundle bundle = new Bundle();

                bundle.putInt(Recipes.RECIPE_POSITION, position);

                //SENDING IN THE RECIPE STEPS
                bundle.putParcelableArrayList(Recipes.RECIPE_KEY, recipesSteps);
                //SENDING IN IF ITS A TABLET LAYOUT OR NOT
                bundle.putBoolean("LAYOUT", isTwoPane);
                stepsDetailFragment.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();

                //CHECKING IF ITS A TABLET OR PHONE AND PLACING IN APPROPRIATE FRAGMENTS
                if(isTwoPane){
                    transaction.replace(R.id.tablet_second_fragment, stepsDetailFragment).commit();
                }else {
                    transaction.replace(R.id.recipe_details_fragment_container, stepsDetailFragment)
                            .commit();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipesSteps.size();
    }

    public class RecipeDetailsViewHolder extends RecyclerView.ViewHolder {

        TextView cardTextView;

        public RecipeDetailsViewHolder(View itemView) {
            super(itemView);
            cardTextView = itemView.findViewById(R.id.simple_card_name);
        }
    }
}
