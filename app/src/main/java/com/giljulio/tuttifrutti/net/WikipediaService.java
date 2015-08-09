package com.giljulio.tuttifrutti.net;

import com.giljulio.tuttifrutti.model.Article;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Gil on 09/08/15.
 */
public interface WikipediaService {

    @GET("/api.php?action=query&prop=extracts&format=json&exintro")
    void getArticleSnippet(@Query("titles") String title, Callback<Article> cb);
}
