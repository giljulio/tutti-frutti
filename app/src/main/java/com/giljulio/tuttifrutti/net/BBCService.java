package com.giljulio.tuttifrutti.net;

import com.giljulio.tuttifrutti.model.FruitList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Gil on 07/08/15.
 */
public interface BBCService {

    @GET("/data.json")
    void getFruitList(Callback<FruitList> cb);

    //TODO: If a REST request changes data on the server it should be a POST request
    @GET("/stats")
    void postStats(@Query("event") String event);

    @GET("/stats")
    void postStats(@Query("event") String event, @Query("data") Object data);

}
