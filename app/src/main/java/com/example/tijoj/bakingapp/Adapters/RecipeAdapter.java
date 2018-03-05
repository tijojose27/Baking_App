package com.example.tijoj.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tijoj.bakingapp.R;
import com.example.tijoj.bakingapp.RecipeActivity;
import com.example.tijoj.bakingapp.data.Recipes;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by tijoj on 2/21/2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipiesViewHolder>{

    //ADAPTER FOR THE RECIPE LIST
    public List<Recipes> recipesList;
    public Context context;

    public RecipeAdapter(Context context, List<Recipes> recipesList){
        this.context = context;
        this.recipesList = recipesList;
    }

    @Override
    public RecipiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.simple_cards, null);
        return new RecipiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipiesViewHolder holder, int position) {
        final Recipes recipe = recipesList.get(position);

        //SETTING THE RECIPIE NAME
        holder.cardTextView.setText(recipe.getRecipeName());

        //ONCLICK LISTENER ON THE RECYCLER VIEW
        holder.cardTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, RecipeActivity.class);
                //SENDING IN RECPIE AS A PARCELABLE TO THE NEXT ACTIVITY
                intent.putExtra(Recipes.RECIPE_KEY, recipe);
                context.startActivity(intent);
            }
        });

        if(recipe.getImage().isEmpty()){
            holder.imageView.setVisibility(View.GONE);
        }else{
            holder.imageView.setVisibility(View.VISIBLE);
            Picasso.with(context).load(recipe.getImage()).into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }


    class RecipiesViewHolder extends RecyclerView.ViewHolder {

        TextView cardTextView;

        ImageView imageView;

        public RecipiesViewHolder(View itemView) {
            super(itemView);
            cardTextView = itemView.findViewById(R.id.simple_card_name);
            imageView= itemView.findViewById(R.id.image_simple_card);
        }
    }
}
