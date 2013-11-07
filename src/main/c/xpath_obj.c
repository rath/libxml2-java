#include "rath_libxml_XPathObject.h"
#include "utils.h"

/*
 * Class:     rath_libxml_XPathObject
 * Method:    disposeImpl
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_rath_libxml_XPathObject_disposeImpl
(JNIEnv *env, jobject obj) {
    xmlXPathObject *object = findXPathObject(env, obj);  
    xmlXPathFreeObject(object);
}
