package com.cinema.client.utils;

import android.util.Log;
import android.widget.Toast;

import com.cinema.client.entities.Hall;
import com.google.gson.Gson;

public class HallJsonParser {



    public Hall jsonFooBar(){

        String jsonTest="{\n" +
                "\t\"row\":5,\n" +
                "\t\"col\":5,\n" +
                "\t\"booked\":[\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":0,\n" +
                "\t\t\t\"col\":0\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":1,\n" +
                "\t\t\t\"col\":1\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":2,\n" +
                "\t\t\t\"col\":2\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":3,\n" +
                "\t\t\t\"col\":3\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":4,\n" +
                "\t\t\t\"col\":4\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"free\":[\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":0,\n" +
                "\t\t\t\"col\":2\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":1,\n" +
                "\t\t\t\"col\":2\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":2,\n" +
                "\t\t\t\"col\":0\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":3,\n" +
                "\t\t\t\"col\":0\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":4,\n" +
                "\t\t\t\"col\":0\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"disabled\":[\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":0,\n" +
                "\t\t\t\"col\":1\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":0,\n" +
                "\t\t\t\"col\":3\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":4,\n" +
                "\t\t\t\"col\":1\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":4,\n" +
                "\t\t\t\"col\":2\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":4,\n" +
                "\t\t\t\"col\":3\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"bought\":[\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":0,\n" +
                "\t\t\t\"col\":4\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":1,\n" +
                "\t\t\t\"col\":0\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":1,\n" +
                "\t\t\t\"col\":3\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":1,\n" +
                "\t\t\t\"col\":4\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":2,\n" +
                "\t\t\t\"col\":4\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":3,\n" +
                "\t\t\t\"col\":1\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":3,\n" +
                "\t\t\t\"col\":2\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"row\":3,\n" +
                "\t\t\t\"col\":4\n" +
                "\t\t}\n" +
                "\t]"+
                "}";



        Gson gson = new Gson();
        Hall parsed= gson.fromJson(jsonTest,Hall.class);

        Log.d("PARSED Booked",parsed.getBooked().size()+" ");
        Log.d("PARSED Free",parsed.getFree().size()+" ");


        return parsed;

    }




}
