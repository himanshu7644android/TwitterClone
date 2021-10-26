package com.examplmakecodeeasy.twitterclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("LVKsbjFTfunpJTeFPXfqFOIrIlgQWBAo0Jwr1ZVF")
                // if defined
                .clientKey("ZiJ92UU8X1npwTyjZwplorOXSsRtH7Nwu3Y62Z3T")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
