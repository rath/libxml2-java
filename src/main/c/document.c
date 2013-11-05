#include "rath_libxml_Document.h"
#include <libxml/parser.h>
#include "utils.h"

/*
 * Class:     rath_libxml_Document
 * Method:    getRootElementImpl
 * Signature: ()Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_Document_getRootElementImpl
  (JNIEnv *env, jobject obj) {
    xmlDoc *doc = findDocument(env, obj);
    xmlNode *rootNode = xmlDocGetRootElement(doc);
    return buildNode(env, rootNode, obj);
}

/*
 * Class:     rath_libxml_Document
 * Method:    disposeImpl
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_rath_libxml_Document_disposeImpl
  (JNIEnv *env, jobject obj) {
    xmlDoc *doc = findDocument(env, obj);
    xmlFreeDoc(doc);
}

/*
 * Class:     rath_libxml_Document
 * Method:    createXPathContextImpl
 * Signature: ()Lrath/libxml/XPathContext;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_Document_createXPathContextImpl
  (JNIEnv *env, jobject obj) {
    xmlDoc *doc = findDocument(env, obj);
    xmlXPathContext *ctx = xmlXPathNewContext(doc);
    return buildXPathContext(env, ctx);
}
