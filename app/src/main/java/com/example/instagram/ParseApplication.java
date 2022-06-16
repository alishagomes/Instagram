package com.example.instagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * for implementing Parse in application
 */

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /** Register your parse models */
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("aPAkiflfdHntjj2eBdpochEuSHJEL0uMvkSwxNj7")
                .clientKey("f33K8O00XiSvyqOdlDiDccK2kv5DsCYufyltUPba")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
