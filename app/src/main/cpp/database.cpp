#include <jni.h>
#include <string>
#include <cstring>
using namespace std;
//
// Created by oocha on 2021/11/25.
//

extern "C"
JNIEXPORT jstring JNICALL
Java_com_websarva_wings_android_databasevulnapp_viewmodel_database_DatabaseViewModel_getXorData(
        JNIEnv *env, jobject thiz, jint flag) {
    // TODO: implement getXorData()
    string retValue;

    switch (flag) {
        case 0:
            retValue += "aHZXaXBEZEMzOE0zZlZMNXY5OFlGVHRyYzZiMmtjU3A=";
            break;
        case 1:
            retValue += "S0VCWFY5TlZWS0MzbUd3YlE0VEhiVlBHRUtQcXBLZGs=";
            break;
        default:
            retValue += "Error";
            break;
    }

    return env->NewStringUTF(retValue.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_websarva_wings_android_databasevulnapp_viewmodel_database_DatabaseViewModel_getAESData(
        JNIEnv *env, jobject thiz, jint flag) {
    // TODO: implement getAESData()
    string retValue;

    switch (flag) {
        case 0:
            retValue += "Rg8pNBEiWgUMLj96Wy4ZTQ95FRslHTk/AU4ub0EbHj8=";
            break;
        case 1:
            retValue += "aM53BnS5u+xQTVN/qETYJA==";
            break;
        case 2:
            retValue = "Z3UrASbvv70iKWY4Jnw7eBtTCFIrPhNmPSd/FCIJKglYAQ==";
            break;
        case 3:
            retValue = "AvLVCmfHnChKbI7ZVEds5Vq8Yu6/iQewTSAQK36zGaw3xxyavfmHAvRgoHYT9dLy";
            break;
        default:
            retValue += "Error";
            break;
    }

    return env->NewStringUTF(retValue.c_str());
}