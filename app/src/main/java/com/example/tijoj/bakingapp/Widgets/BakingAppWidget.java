package com.example.tijoj.bakingapp.Widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.tijoj.bakingapp.R;
import com.example.tijoj.bakingapp.data.Recipes;

import java.util.StringTokenizer;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    //WIDGET CAN ONLY BE CALLED ONCE THE APP IS OPENED AND THE FAVORITE RECIPE IS SELECTED
    // OTHER WISE IT CRASHES

    public SharedPreferences sharedPreferences;

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String recipeName, String[] ingredients) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

        //GETTING THE RECIPIE NAME AND DYNAMICALLY CREATING VIEWS
        if(recipeName!=null && ingredients.length>0) {
            views.removeAllViews(R.id.ingredient_container);
            views.setTextViewText(R.id.recipe_name_tv, recipeName);

            for (String ingredient : ingredients) {
                RemoteViews singleView = new RemoteViews(context.getPackageName(), R.layout.ingredient_list_item);

                singleView.setTextViewText(R.id.ingredient_name, ingredient);
                views.addView(R.id.ingredient_container, singleView);
            }
        }else{
            views.setTextViewText(R.id.recipe_name_tv, "NEED TO ADD INGREDIENTS FROM APP");
        }


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        sharedPreferences = context.getSharedPreferences("MyPref", 0);

        //GETTING THE DATA FROM SHARED PREFERENCES
        String recipeName = sharedPreferences.getString(Recipes.RECIPE_NAME, null);
        String listIngreds = sharedPreferences.getString(Recipes.RECIPE_INGREDS, null);

        String[] ingreds = listIngreds.split(",");

        if (ingreds.length > 0) {

            for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId, recipeName, ingreds);
            }
        }
    }

    //NONE OF THESE METHODS ARE USED

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

