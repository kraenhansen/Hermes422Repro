#include <jni.h>
#include <android/log.h>
#include <jsi/jsi.h>

namespace jsi = facebook::jsi;

extern "C"
JNIEXPORT void JNICALL
Java_com_hermes422repro_Hermes422Module_install(JNIEnv *env, jobject thiz, jlong runtime_pointer) {
    __android_log_print(ANDROID_LOG_VERBOSE, "Hermes422Module", "installed");
    // auto runtime = *reinterpret_cast<jsi::Runtime*>(runtime_pointer);
    auto &runtime = *((jsi::Runtime*) runtime_pointer);
    auto global = runtime.global();
    // auto str = jsi::String::createFromUtf8(runtime, "hi there!");
    auto echo = jsi::Function::createFromHostFunction(
        runtime,
        jsi::PropNameID::forAscii(runtime, "echo"),
        1,
        [](jsi::Runtime& rt, const jsi::Value& thisVal, const jsi::Value* args, size_t count){
            if (count != 1) {
                throw std::runtime_error("Expected exactly one argument");
            }
            auto callback = args[0].asObject(rt).asFunction(rt);
            return callback.call(rt);
        });
    // TODO: Isn't there a better way to expose this? Something that would end up as NativeModules.Hermes422Repro.echo
    global.setProperty(runtime, "echo", echo);
}