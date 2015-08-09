package com.giljulio.tuttifrutti.net;

import com.giljulio.tuttifrutti.model.Article;
import com.giljulio.tuttifrutti.model.FruitList;

import junit.framework.TestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Gil on 09/08/15.
 */
public class APITest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testBBCService() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);
        final boolean[] statuses = {false, false};

        API.getBBCServiceInstance().getFruitList(new Callback<FruitList>() {
            @Override
            public void success(FruitList fruitList, Response response) {
                statuses[0] = true;
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        API.getBBCServiceInstance().postStats("load", 1000F, new Callback() {
            @Override
            public void success(Object o, Response response) {
                statuses[1] = true;

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


        signal.await(10, TimeUnit.SECONDS);
        for (boolean status : statuses)
            assertTrue(status);

    }

    public void testWikipediaService() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);
        final boolean[] status = {false};

        API.getWikipediaInstance().getArticleSnippet("android", new Callback<Article>() {
            @Override
            public void success(Article article, Response response) {
                status[0] = true;
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


        signal.await(10, TimeUnit.SECONDS);
        for (boolean s : status)
            assertTrue(s);
    }
}