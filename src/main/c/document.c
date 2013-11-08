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


/*
 * Class:     rath_libxml_Document
 * Method:    createElementImpl
 * Signature: (Ljava/lang/String;)Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_Document_createElementImpl
(JNIEnv *env, jobject obj, jstring jname) {
    xmlDoc *doc = findDocument(env, obj);
    xmlNode *created;
    const char *name = (*env)->GetStringUTFChars(env, jname, NULL);
    created = xmlNewDocNode(doc, NULL, (xmlChar*)name, NULL);
    (*env)->ReleaseStringUTFChars(env, jname, name);
    return buildNode(env, created, obj);
}

/*
 * Class:     rath_libxml_Document
 * Method:    createTextImpl
 * Signature: (Ljava/lang/String;)Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_Document_createTextImpl
(JNIEnv *env, jobject obj, jstring jstr) {
    xmlDoc *doc = findDocument(env, obj);
    
    const char *str = (*env)->GetStringUTFChars(env, jstr, NULL);
    xmlNode *textNode = xmlNewText((xmlChar*)str);
    (*env)->ReleaseStringUTFChars(env, jstr, str);
    textNode->doc = doc; // TODO: Is it safe? are you sure?
    
    return buildNode(env, textNode, obj);
}