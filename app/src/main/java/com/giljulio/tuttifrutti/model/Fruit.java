package com.giljulio.tuttifrutti.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Gil on 07/08/15.
 */
public class Fruit implements Serializable {

    @SerializedName("type")
    public String name;

    public int price;

    public int weight;

    @Nullable
    public FruitMedia media;

    @Nullable
    public int color;
}
