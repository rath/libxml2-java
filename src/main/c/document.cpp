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
    return buildNode(env, rootNode);
}

/*
 * Class:     rath_libxml_Document
 * Method:    dispose
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_rath_libxml_Document_dispose
  (JNIEnv *env, jobject obj) {
    xmlDoc *doc = findDocument(env, obj);
    xmlFreeDoc(doc);
}

