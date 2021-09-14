package com.hermes422repro;

import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.JavaScriptContextHolder;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.turbomodule.core.interfaces.TurboModule;
import com.facebook.soloader.SoLoader;

public class Hermes422Module extends ReactContextBaseJavaModule {
    static String NAME = "Hermes422Repro";

    static {
        SoLoader.loadLibrary("hermes422repro");
    }

    public Hermes422Module(ReactApplicationContext reactContext) {
        Log.d("Hermes422Module", "constructing");
        JavaScriptContextHolder contextHolder = reactContext.getJavaScriptContextHolder();
        synchronized (contextHolder) {
            install(contextHolder.get());
        }
    }

    private native void install(long runtimePointer);

    @NonNull
    @Override
    public String getName() {
        return NAME;
    }
}
