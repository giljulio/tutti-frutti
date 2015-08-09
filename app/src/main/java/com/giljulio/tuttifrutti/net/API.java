package com.giljulio.tuttifrutti.net;

import com.giljulio.tuttifrutti.utils.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.Profiler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by Gil on 07/08/15.
 */
public class API {
    
    private static final Logger LOG = Logger.create();

    private static final String BBC_API_URL = "https://raw.githubusercontent.com/fmtvp/recruit-test-data/master";
    private static final String WIKIPEDIA_API_URL = "https://en.wikipedia.org/w";

    private static BBCService sBBCService;
    private static WikipediaService sWikipediaService;

    private static Profiler sStatProfiler = new Profiler() {
        @Override
        public Object beforeCall() {
            return null;
        }

        @Override
        public void afterCall(RequestInformation requestInfo, long elapsedTime, int statusCode, Object beforeCallData) {
            if(!requestInfo.getRelativePath().contains("stats")){
                sendStatsReport(elapsedTime);
            }
        }
    };

    private static ErrorHandler sErrorHandler = new ErrorHandler() {
        @Override
        public Throwable handleError(RetrofitError cause) {
            if (!cause.getUrl().contains("stats")) {
                sendErrorReport(cause);
            }
            return cause;
        }
    };

    public static synchronized BBCService getBBCServiceInstance(){
        if(sBBCService == null){
            Gson gson = new GsonBuilder().create();
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(BBC_API_URL)
                    .setLogLevel(RestAdapter.LogLevel.NONE)
                    .setConverter(new GsonConverter(gson))
                    .setErrorHandler(sErrorHandler)
                    .setProfiler(sStatProfiler)
                    .build();

            sBBCService = restAdapter.create(BBCService.class);
        }

        return sBBCService;
    }

    public static synchronized WikipediaService getWikipediaInstance(){
        if(sWikipediaService == null){
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(WIKIPEDIA_API_URL)
                    .setLogLevel(RestAdapter.LogLevel.NONE)
                    .setConverter(new DynamicJsonConverter())
                    .setProfiler(sStatProfiler)
                    .build();

            sWikipediaService = restAdapter.create(WikipediaService.class);
        }
        return sWikipediaService;
    }

    public static void sendErrorReport(Throwable ex) {
        getBBCServiceInstance().postStats("error", ex.getMessage(), new Callback<Object>() {
            @Override
            public void success(Object o, Response response) {
                LOG.d("Error report sent. ");
            }

            @Override
            public void failure(RetrofitError error) {
                LOG.e("Failed to send error report");
            }
        });
    }

    private static void sendStatsReport(long elapsedTime) {
        getBBCServiceInstance().postStats("load", elapsedTime, new Callback<Object>() {
            @Override
            public void success(Object o, Response response) {
                LOG.d("Sent stats report");
            }

            @Override
            public void failure(RetrofitError error) {
                LOG.e("Failed to send stats report");
            }
        });
    }
}
