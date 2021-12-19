#include "app_ScalarProduct.h"
#include <stdio.h>
#include <Windows.h>

JNIEXPORT jobject JNICALL Java_app_ScalarProduct_multi01
(JNIEnv* env, jobject object, jobjectArray a, jobjectArray b) {

    // Get vectors legths
    jsize lengthVectorA = (*env)->GetArrayLength(env, a);
    jsize lengthVectorB = (*env)->GetArrayLength(env, b);

    // Compare vectors lengths and use the shorter one to compute the result
    int length = lengthVectorA;
    if (length > lengthVectorB)
        length = lengthVectorB;

    // Find Java Double class
    jclass doubleClass = (*env)->FindClass(env, "java/lang/Double");
    // Get value of Double object method
    jmethodID doubleValue = (*env)->GetMethodID(env, doubleClass, "doubleValue", "()D");
    int i = 0;
    double result = 0.0;
    double doubleA = 0.0;
    double doubleB = 0.0;
    jobject elementA=(*env)->GetObjectArrayElement(env, a, i);
    jobject elementB=(*env)->GetObjectArrayElement(env, b, i);

    for (i; i < length; i++)
    {
        // Get vectors elements
        elementA = (*env)->GetObjectArrayElement(env, a, i);
        elementB = (*env)->GetObjectArrayElement(env, b, i);
        // Get double values of vector elements
        doubleA = (*env)->CallDoubleMethod(env, elementA, doubleValue);
        doubleB = (*env)->CallDoubleMethod(env, elementB, doubleValue);
        result += doubleA * doubleB;
        
    }

    // Get Double constructor
    jmethodID doubleConstructor = (*env)->GetMethodID(env, doubleClass, "<init>", "(D)V");

    if (NULL == doubleConstructor)
    {
        printf("Failed to initialize Double object!\n");
        fflush(stdout);
        
        return a;
    }
    // Create Double object
    jobject newDoubleObj = (*env)->NewObject(env, doubleClass, doubleConstructor, result);

    // Delete references
    (*env)->DeleteLocalRef(env, a);
    (*env)->DeleteLocalRef(env, b);
    //(*env)->DeleteLocalRef(env, lengthVectorA);
    //(*env)->DeleteLocalRef(env, lengthVectorB);
    (*env)->DeleteLocalRef(env, object);
    (*env)->DeleteLocalRef(env, elementA);
    (*env)->DeleteLocalRef(env, elementB);
    (*env)->DeleteLocalRef(env, doubleClass);

    return newDoubleObj;
}

JNIEXPORT jobject JNICALL Java_app_ScalarProduct_multi02
(JNIEnv* env, jobject object, jobjectArray a) {
    // Get object Class
    jclass objectClass = (*env)->GetObjectClass(env, object);
    // Try to find object's field "b" of type Double[] 
    jfieldID bID = (*env)->GetFieldID(env, objectClass, "b", "[Ljava/lang/Double;");
    if (bID == 0)
    {
        printf("Failed to read field b!\n");
        fflush(stdout);
        return a;
    }
    jobjectArray b = (*env)->GetObjectField(env, object, bID);

    (*env)->DeleteLocalRef(env, objectClass);
    return Java_app_ScalarProduct_multi01(env, object, a, b);
}


JNIEXPORT void JNICALL Java_app_ScalarProduct_multi03
(JNIEnv* env, jobject object) {

    jclass objectClass = (*env)->GetObjectClass(env, object);

    jfieldID inputFormID = (*env)->GetFieldID(env, objectClass, "inputForm", "Lapp/InputForm;");
    if (inputFormID == 0)
    {
        printf("Failed get FieldId!\n");
        fflush(stdout);
        return;
    }
    jobject inputForm = (*env)->GetObjectField(env, object, inputFormID);
    jclass inputFormClass = (*env)->GetObjectClass(env, inputForm);
    jmethodID openID = (*env)->GetMethodID(env, inputFormClass, "open", "()V");
    if (openID == 0)
    {
        printf("Failed get openID!\n");
        fflush(stdout);
        return;
    }
    (*env)->CallVoidMethod(env, inputForm, openID);

    jfieldID cID = (*env)->GetFieldID(env, objectClass, "c", "Ljava/lang/Double;");

    if (cID == 0)
    {
        printf("Failed to read field 'c'!\n");
        fflush(stdout);
        return;
    }

    jmethodID multi04ID = (*env)->GetMethodID(env, objectClass, "multi04", "()V");
    if (multi04ID == 0)
    {
        printf("Failed to read method multi04!\n");
        fflush(stdout);
        return;
    }

    (*env)->CallVoidMethod(env, object, multi04ID);
    jobject c = (*env)->GetObjectField(env, object, cID);

    jclass doubleClass = (*env)->FindClass(env, "java/lang/Double");
    jmethodID doubleValueID = (*env)->GetMethodID(env, doubleClass, "doubleValue", "()D");
    jdouble result = (*env)->CallDoubleMethod(env, c, doubleValueID);

    (*env)->SetDoubleField(env, objectClass, cID, result);

    (*env)->DeleteLocalRef(env, objectClass);
    (*env)->DeleteLocalRef(env, object);
    (*env)->DeleteLocalRef(env, doubleClass);
    (*env)->DeleteLocalRef(env, c);
    (*env)->DeleteLocalRef(env, inputFormClass);
    (*env)->DeleteLocalRef(env, inputForm);
    return;
}