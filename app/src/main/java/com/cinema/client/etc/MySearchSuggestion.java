package com.cinema.client.etc;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

@SuppressLint("ParcelCreator")
public class MySearchSuggestion implements SearchSuggestion {

    String text;

    public MySearchSuggestion(String text) {
        this.text = text;
    }

    @Override
    public String getBody() {
        return text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

}
