package com.hermes422repro;

import com.facebook.react.TurboReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;

import java.util.HashMap;
import java.util.Map;

public class Hermes422Package extends TurboReactPackage {

    @Override
    public NativeModule getModule(String name, ReactApplicationContext reactContext) {
        if (name.equals(Hermes422Module.NAME)) {
            return new Hermes422Module(reactContext);
        } else {
            return null;
        }
    }

    @Override
    public ReactModuleInfoProvider getReactModuleInfoProvider() {
        return new ReactModuleInfoProvider() {
            @Override
            public Map<String, ReactModuleInfo> getReactModuleInfos() {
                Map reactModuleInfoMap = new HashMap();
                reactModuleInfoMap.put(Hermes422Module.NAME, new ReactModuleInfo(
                        Hermes422Module.NAME,
                        "com.hermes422repro.Hermes422Module",
                        false,
                        false,
                        false,
                        false,
                        true
                ));
                return reactModuleInfoMap;
            }
        };
    }
}
