package com.giljulio.tuttifrutti;

import android.app.Application;

import com.giljulio.tuttifrutti.net.API;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Gil on 07/08/15.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread thread, Throwable ex) {

                // Print throwable
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);

                // Send crash report
                API.sendErrorReport(ex);
            }
        });
    }
}
