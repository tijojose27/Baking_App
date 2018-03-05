package com.example.tijoj.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tijoj on 2/21/2018.
 */

public class RecipesSteps implements Parcelable{

    public static final String STEPS_KEY = "RECIPE DETAILS";

    public int recipieStepsId;
    public String shortDescription;
    public String description;
    public String videoURL;
    public String thumbnamilUrl;

    public RecipesSteps(int recipieStepsId, String shortDescription, String description, String videoURL, String thumbnamilUrl) {
        this.recipieStepsId = recipieStepsId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnamilUrl = thumbnamilUrl;
    }

    protected RecipesSteps(Parcel in) {
        recipieStepsId = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnamilUrl = in.readString();
    }

    public static final Creator<RecipesSteps> CREATOR = new Creator<RecipesSteps>() {
        @Override
        public RecipesSteps createFromParcel(Parcel in) {
            return new RecipesSteps(in);
        }

        @Override
        public RecipesSteps[] newArray(int size) {
            return new RecipesSteps[size];
        }
    };


    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnamilUrl() {
        return thumbnamilUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(recipieStepsId);
        parcel.writeString(shortDescription);
        parcel.writeString(description);
        parcel.writeString(videoURL);
        parcel.writeString(thumbnamilUrl);
    }
}
