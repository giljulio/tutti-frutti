package com.giljulio.tuttifrutti.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gil on 07/08/15.
 */
public class Fruit {

    @SerializedName("type")
    String name;

    int price;

    int weight;
}
