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
 * Method:    setVersionImpl
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_rath_libxml_Document_setVersionImpl
(JNIEnv *env, jobject obj, jstring jVersion) {
    xmlDoc *doc = findDocument(env, obj);
    xmlFree((void*)doc->version);
    const char *version = (*env)->GetStringUTFChars(env, jVersion, NULL);
    doc->version = xmlStrdup((xmlChar*)version);
    (*env)->ReleaseStringUTFChars(env, jVersion, version);
}

/*
 * Class:     rath_libxml_Document
 * Method:    getVersionImpl
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_rath_libxml_Document_getVersionImpl
(JNIEnv *env, jobject obj) {
    xmlDoc *doc = findDocument(env, obj);
    return (*env)->NewStringUTF(env, (const char*)doc->version);
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
    jsize len = (*env)->GetStringUTFLength(env, jstr);
    xmlNode *textNode = xmlNewDocTextLen(doc, (xmlChar*)str, len);
    (*env)->ReleaseStringUTFChars(env, jstr, str);
    
    return buildNode(env, textNode, obj);
}

/*
 * Class:     rath_libxml_Document
 * Method:    createCommentImpl
 * Signature: (Ljava/lang/String;)Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_Document_createCommentImpl
(JNIEnv *env, jobject obj, jstring jstr) {
//    xmlDoc *doc = findDocument(env, obj);
    
    const char *str = (*env)->GetStringUTFChars(env, jstr, NULL);
    xmlNode *commentNode = xmlNewComment((xmlChar*)str);
    (*env)->ReleaseStringUTFChars(env, jstr, str);
    
    return buildNode(env, commentNode, obj);
}


/*
 * Class:     rath_libxml_Document
 * Method:    createCDataImpl
 * Signature: (Ljava/lang/String;)Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_Document_createCDataImpl
(JNIEnv *env, jobject obj, jstring jstr) {
    xmlDoc *doc = findDocument(env, obj);
    
    const char *str = (*env)->GetStringUTFChars(env, jstr, NULL);
    jsize len = (*env)->GetStringUTFLength(env, jstr);
    xmlNode *cdata = xmlNewCDataBlock(doc, (xmlChar*)str, len);
    (*env)->ReleaseStringUTFChars(env, jstr, str);
    
    return buildNode(env, cdata, obj);
}

/*
 * Class:     rath_libxml_Document
 * Method:    createPIImpl
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_Document_createPIImpl
(JNIEnv *env, jobject obj, jstring jname, jstring jcontent) {
    xmlDocPtr doc = findDocument(env, obj);
    const char *name = (*env)->GetStringUTFChars(env, jname, NULL);
    const char *content = (*env)->GetStringUTFChars(env, jcontent, NULL);
    
    xmlNodePtr node = xmlNewDocPI(doc, (const xmlChar*)name, (const xmlChar*)content);
    
    (*env)->ReleaseStringUTFChars(env, jname, name);
    (*env)->ReleaseStringUTFChars(env, jcontent, content);
    
    return buildNode(env, node, obj);
}

/*
 * Class:     rath_libxml_Document
 * Method:    createDocumentImpl
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_rath_libxml_Document_createDocumentImpl
(JNIEnv *env, jclass clz, jstring jVersion) {
    const char *version = (*env)->GetStringUTFChars(env, jVersion, NULL);
    xmlDocPtr doc = xmlNewDoc((const xmlChar*)version);
    (*env)->ReleaseStringUTFChars(env, jVersion, version);
    return (jlong)doc;
}

/*
 * Class:     rath_libxml_Document
 * Method:    saveImpl
 * Signature: (Ljava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_rath_libxml_Document_saveImpl
(JNIEnv *env, jobject obj, jstring jpath, jstring jencoding) {
    xmlDoc *doc = findDocument(env, obj);
    const char *path = (*env)->GetStringUTFChars(env, jpath, NULL);
    const char *encoding = (*env)->GetStringUTFChars(env, jencoding, NULL);
    
    int ret = xmlSaveFileEnc(path, doc, encoding);
    
    (*env)->ReleaseStringUTFChars(env, jpath, path);
    (*env)->ReleaseStringUTFChars(env, jencoding, encoding);
    
    if (ret < 1) {
        throwInternalErrorWithLastError(env);
    }
}