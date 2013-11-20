#include "org_xmlsoft_XPathObject.h"
#include "utils.h"

/*
 * Class:     org_xmlsoft_XPathObject
 * Method:    castToStringImpl
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_xmlsoft_XPathObject_castToStringImpl
(JNIEnv *env, jobject obj) {
    xmlXPathObject *result = findXPathObject(env, obj);
    xmlChar *value = xmlXPathCastToString(result);
    jstring jvalue = (*env)->NewStringUTF(env, (const char*)value);
    xmlFree(value);
    return jvalue;
}

/*
 * Class:     org_xmlsoft_XPathObject
 * Method:    castToNumberImpl
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_org_xmlsoft_XPathObject_castToNumberImpl
(JNIEnv *env, jobject obj) {
    xmlXPathObject *result = findXPathObject(env, obj);
    double value = xmlXPathCastToNumber(result);
    return (jdouble)value;
}

/*
 * Class:     org_xmlsoft_XPathObject
 * Method:    castToBooleanImpl
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_org_xmlsoft_XPathObject_castToBooleanImpl
(JNIEnv *env, jobject obj) {
    xmlXPathObject *result = findXPathObject(env, obj);
    int value = xmlXPathCastToBoolean(result);
    return value==0 ? JNI_FALSE : JNI_TRUE;
}

/*
 * Class:     org_xmlsoft_XPathObject
 * Method:    disposeImpl
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_XPathObject_disposeImpl
(JNIEnv *env, jobject obj) {
    xmlXPathObject *object = findXPathObject(env, obj);  
    xmlXPathFreeObject(object);
}
