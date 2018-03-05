package com.example.tijoj.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tijoj on 2/21/2018.
 */

public class RecipesIngredients implements Parcelable{

    //SIMPLE POJO FOR RECIPIE INGREDIENTS WITH IMPLEMENTS PARCELABLES

    public static final String INGREDIENTS_KEY = "RECIPE INGREDIENTS";

    public double quantity;
    public String measure;
    public String ingreditent;

    public RecipesIngredients(double quantity, String measure, String ingreditent) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingreditent = ingreditent;
    }

    protected RecipesIngredients(Parcel in) {
        quantity = in.readDouble();
        measure = in.readString();
        ingreditent = in.readString();
    }

    public static final Creator<RecipesIngredients> CREATOR = new Creator<RecipesIngredients>() {
        @Override
        public RecipesIngredients createFromParcel(Parcel in) {
            return new RecipesIngredients(in);
        }

        @Override
        public RecipesIngredients[] newArray(int size) {
            return new RecipesIngredients[size];
        }
    };

    public String getQuantity() {
        return String.valueOf(quantity);
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngreditent() {
        return ingreditent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingreditent);
    }
}
