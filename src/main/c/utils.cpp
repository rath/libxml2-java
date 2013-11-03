#include "utils.h"

jlong findBaseNode(JNIEnv *env, jobject obj);

jobject buildNode(JNIEnv *env, xmlNode *node) {
    jlong pNode = (jlong)node;
    jclass nodeClass = env->FindClass("rath/libxml/Node");
    jmethodID constructorWithPointer = env->GetMethodID(nodeClass, "<init>", "(J)V");
    return env->NewObject(nodeClass, constructorWithPointer, pNode);
}

jobject buildDocument(JNIEnv *env, xmlDoc *doc) {
    jlong pDoc = (jlong)doc;
    jclass nodeClass = env->FindClass("rath/libxml/Document");
    jmethodID constructorWithPointer = env->GetMethodID(nodeClass, "<init>", "(J)V");
    return env->NewObject(nodeClass, constructorWithPointer, pDoc);
}


xmlDocPtr   findDocument(JNIEnv *env, jobject obj) {
    return (xmlDocPtr)findBaseNode(env, obj);
}

xmlNodePtr  findNode(JNIEnv *env, jobject obj) {
    return (xmlNodePtr)findBaseNode(env, obj);
}

jlong findBaseNode(JNIEnv *env, jobject obj) {
    jfieldID fieldP = env->GetFieldID(env->GetObjectClass(obj), "p", "J");
    jlong pThis = env->GetLongField(obj, fieldP);
    return pThis;
}

