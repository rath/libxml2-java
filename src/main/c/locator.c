#include "org_xmlsoft_impl_LocatorImpl.h"
#include "utils.h"

/*
 * Class:     org_xmlsoft_impl_LocatorImpl
 * Method:    getPublicIdImpl
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_xmlsoft_impl_LocatorImpl_getPublicIdImpl
(JNIEnv *env, jobject obj) {
    LocatorContext *c = findLocator(env, obj);
    const xmlChar *publicId = c->locator->getPublicId(c->parser);
    return (*env)->NewStringUTF(env, (const char*)publicId);
}

/*
 * Class:     org_xmlsoft_impl_LocatorImpl
 * Method:    getSystemIdImpl
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_xmlsoft_impl_LocatorImpl_getSystemIdImpl
(JNIEnv *env, jobject obj) {
    LocatorContext *c = findLocator(env, obj);
    const xmlChar *systemId = c->locator->getSystemId(c->parser);
    return (*env)->NewStringUTF(env, (const char*)systemId);
}

/*
 * Class:     org_xmlsoft_impl_LocatorImpl
 * Method:    getLineNumberImpl
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_xmlsoft_impl_LocatorImpl_getLineNumberImpl
(JNIEnv *env, jobject obj) {
    LocatorContext *c = findLocator(env, obj);
    int lineNo = c->locator->getLineNumber(c->parser);
    return (jint)lineNo;
}

/*
 * Class:     org_xmlsoft_impl_LocatorImpl
 * Method:    getColumnNumberImpl
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_xmlsoft_impl_LocatorImpl_getColumnNumberImpl
(JNIEnv *env, jobject obj) {
    LocatorContext *c = findLocator(env, obj);
    int colNo = c->locator->getColumnNumber(c->parser);
    return (jint)colNo;
}
