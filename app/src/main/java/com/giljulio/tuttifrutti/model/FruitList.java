package com.giljulio.tuttifrutti.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Gil on 07/08/15.
 */
public class FruitList {

    @SerializedName("fruits")
    ArrayList<Fruit> fruits;
}
