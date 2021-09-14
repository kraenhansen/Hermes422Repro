# Hermes #422 reproduction

A React Native app containing a native module which demonstrates https://github.com/facebook/hermes/issues/422

* This uses React Native v0.66.0-rc.2 with the proposed fix for the issue.

## How does this reproduce the bug?

* A package registers a turbomodule which will call into a native JNI function (`Hermes422Module#install`) implemented in C++ via a shared library.
* We're linking our native C++ module against the newly separated libjsi which gets extracted from the AAR provided by the react-native package via our [CMakeLists.txt](android/app/src/main/cpp/CMakeLists.txt).
* The native `install` function will set a global `echo` function.
* The `echo` function is a `HostFunction` which simply return whatever is returned from calling a callback passed as the first argument.
* Our [App.js](App.js) initialize the turbo module and call the `echo` function with a callback that throws a JavaScript `Error`.

## How to run this

* Install the package dependencies using `yarn` (because of https://github.com/react-native-community/releases/issues/252)
* Open the `android/app` project in Android Studio.
* Run the app and notice how this crash the app instead of simply throwing a JavaScript error.

This is the logcat output at the time of crashing:

```
2021-09-14 14:52:31.140 32569-32637/com.hermes422repro D/Hermes422Module: constructing
2021-09-14 14:52:31.143 32569-32637/com.hermes422repro V/Hermes422Module: installed
2021-09-14 14:52:31.145 32569-32637/com.hermes422repro I/HermesVM: JSI rethrowing JS exception: Here comes the error!
    
    Error: Here comes the error!
        at anonymous (http://10.0.2.2:8081/index.bundle?platform=android&dev=true&minify=false&app=com.hermes422repro&modulesOnly=false&runModule=true:109506:22)
        at echo (native)
        at App (http://10.0.2.2:8081/index.bundle?platform=android&dev=true&minify=false&app=com.hermes422repro&modulesOnly=false&runModule=true:109505:22)
        at renderWithHooks (http://10.0.2.2:8081/index.bundle?platform=android&dev=true&minify=false&app=com.hermes422repro&modulesOnly=false&runModule=true:10830:33)
        at mountIndeterminateComponent (http://10.0.2.2:8081/index.bundle?platform=android&dev=true&minify=false&app=com.hermes422repro&modulesOnly=false&runModule=true:13618:34)
        at beginWork (http://10.0.2.2:8081/index.bundle?platform=android&dev=true&minify=false&app=com.hermes422repro&modulesOnly=false&runModule=true:14559:49)
        at beginWork$1 (http://10.0.2.2:8081/index.bundle?platform=android&dev=true&minify=false&app=com.hermes422repro&
    
    --------- beginning of crash
2021-09-14 14:52:31.150 32569-32637/com.hermes422repro A/libc: Fatal signal 6 (SIGABRT), code -1 (SI_QUEUE) in tid 32637 (mqt_js), pid 32569 (.hermes422repro)
```
