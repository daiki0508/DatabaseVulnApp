#include <jni.h>
#include <string>
#include <cstring>
using namespace std;

//
// Created by oocha on 2021/12/07.
//

extern "C"
JNIEXPORT jstring JNICALL
Java_com_websarva_wings_android_databasevulnapp_viewmodel_log_LogViewModel_getXorData(JNIEnv *env,
                                                                                      jobject thiz) {
    // TODO: implement getXorData()
    string retValue = "MkZ2alh1WHNTZW5DUnN4MnNkMjZBcWl4RFRpRlRRZ3M=";

    return env->NewStringUTF(retValue.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_websarva_wings_android_databasevulnapp_viewmodel_log_LogViewModel_getAESData(JNIEnv *env,
                                                                                      jobject thiz,
                                                                                      jint flag) {
    // TODO: implement getAESData()
    string retValue;
    switch (flag) {
        case 0:
            retValue += "fQpZDzkXIFgPNFRhOwBDaBFuExwOGQcecT41dSUcMiM=";
            break;
        case 1:
            retValue += "dRVPp26egD6c8SaVwLfY5xSsYfFVwtH337Iiv4oZiHk=";
            break;
        default:
            retValue += "Error";
            break;
    }
    return env -> NewStringUTF(retValue.c_str());
}