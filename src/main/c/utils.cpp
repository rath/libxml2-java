#include "utils.h"

jlong findBaseNode(JNIEnv *env, jobject obj);

jobject buildNode(JNIEnv *env, xmlNode *node, jobject parentDocument) {
    jlong pNode = (jlong)node;
    jclass nodeClass = env->FindClass("rath/libxml/Node");
    jmethodID constructorWithPointer = env->GetMethodID(nodeClass, "<init>", "(J)V");
    jobject jnode  = env->NewObject(nodeClass, constructorWithPointer, pNode);

    jclass cz = env->GetObjectClass(parentDocument);
    jmethodID methodGetDocument = env->GetMethodID(cz, "getDocument", "()Lrath/libxml/Document;");
    jobject jdocument = env->CallObjectMethod(parentDocument, methodGetDocument);
    jmethodID methodSetDocument = env->GetMethodID(cz, "setDocument", "(Lrath/libxml/Document;)V");
    env->CallVoidMethod(jnode, methodSetDocument, jdocument);

    return jnode;
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

