package me.ibore.widget.demo;

import android.app.Application;

import me.ibore.widget.UIUtils;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UIUtils.init(this);
    }
}
