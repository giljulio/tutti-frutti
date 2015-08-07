package com.giljulio.tuttifrutti.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.converter.GsonConverter;

/**
 * Created by Gil on 07/08/15.
 */
public class API {

    private static final String BBC_API_URL = "https://raw.githubusercontent.com/fmtvp/recruit-test-data/master";

    private static BBCService sBBCService;

    public static synchronized BBCService getBBCServiceInstance(){
        if(sBBCService == null){

            Gson gson = new GsonBuilder().create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(BBC_API_URL)
                    .setLogLevel(RestAdapter.LogLevel.NONE)
                    .setConverter(new GsonConverter(gson))
                    .setErrorHandler(new ErrorHandler() {
                        @Override
                        public Throwable handleError(RetrofitError cause) {
                            return null;
                        }
                    })
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {

                        }
                    })
                    .build();

            sBBCService = restAdapter.create(BBCService.class);
        }

        return sBBCService;
    }
}