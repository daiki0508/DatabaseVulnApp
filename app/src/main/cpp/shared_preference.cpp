#include <jni.h>
#include <string>
#include <cstring>
using namespace std;
//
// Created by oocha on 2021/11/22.
//

extern "C"
JNIEXPORT jstring JNICALL
Java_com_websarva_wings_android_databasevulnapp_viewmodel_sharedpreference_SharedPreferenceViewModel_getXorData(
        JNIEnv *env, jobject thiz) {
    // TODO: implement getXorData()
    string retValue = "TTJCdU5kN3FoaWZoZlJWd3lSUzVDdWdIYUpWRW1mNDQ=";

    return env->NewStringUTF(retValue.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_websarva_wings_android_databasevulnapp_viewmodel_sharedpreference_SharedPreferenceViewModel_getAESData(
        JNIEnv *env, jobject thiz, jint flag) {
    // TODO: implement getAESData()
    string retValue;
    switch (flag) {
        case 0:
            retValue += "B21GDwcPQgAaHygJEUd4ERpcTy1VYBJDc0RfHjowXUQ=";
            break;
        case 1:
            retValue += "P5qk1Tl2qoEmc1aJbe1GYU1Cawdyh1w62Fglo/gJ8Eo=";
            break;
        default:
            retValue += "Error";
            break;
    }
    return env -> NewStringUTF(retValue.c_str());
}